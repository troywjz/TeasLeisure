package com.zbkj.service.service.impl.tl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.Stall;
import com.zbkj.common.model.tl.StallCategory;
import com.zbkj.common.model.tl.StallDailyStatus;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.tl.StallSearchRequest;
import com.zbkj.common.request.tl.StallSubmissionRequest;
import com.zbkj.common.request.tl.StallVisibilityRequest;
import com.zbkj.common.request.tl.StallDuplicateSearchRequest;
import com.zbkj.common.response.tl.StallDuplicateCandidateResponse;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.tl.StallPublicResponse;
import com.zbkj.service.dao.tl.StallDao;
import com.zbkj.service.dao.tl.StallCategoryDao;
import com.zbkj.service.dao.tl.StallDailyStatusDao;
import com.zbkj.service.service.tl.StallService;
import com.zbkj.service.service.tl.TlModerationEventService;
import com.zbkj.service.service.UserService;
import com.zbkj.common.utils.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;

import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class StallServiceImpl extends ServiceImpl<StallDao, Stall> implements StallService {

    @Resource
    private StallDao stallDao;

    @Resource
    private StallCategoryDao stallCategoryDao;

    @Resource
    private StallDailyStatusDao dailyStatusDao;

    @Resource
    private TlModerationEventService moderationEventService;

    @Resource
    private UserService userService;

    @Resource
    private StallRegionNameResolver regionNameResolver;

    @Override
    public PageInfo<StallPublicResponse> discover(StallSearchRequest request) {
        int page = request.getPage() == null ? 1 : Math.max(1, request.getPage());
        int limit = request.getLimit() == null ? 20 : Math.min(100, Math.max(1, request.getLimit()));
        boolean hasLat = request.getLat() != null;
        boolean hasLng = request.getLng() != null;
        if (hasLat != hasLng) throw new IllegalArgumentException("lat 与 lng 需要同时提供");
        if (request.getRadiusMeters() != null && !hasLat) throw new IllegalArgumentException("附近范围筛选需要当前位置");
        if (request.getSort() != null && !"recent".equals(request.getSort()) && !"distance".equals(request.getSort())) {
            throw new IllegalArgumentException("sort 只支持 recent 或 distance");
        }
        ZoneId businessZone = ZoneId.of("Asia/Shanghai");
        LocalDateTime businessNow = LocalDateTime.now(businessZone);
        LocalDate businessDate = businessNow.toLocalDate();
        LocalTime businessTime = businessNow.toLocalTime();
        java.sql.Date sqlBusinessDate = java.sql.Date.valueOf(businessDate);
        LambdaQueryWrapper<Stall> query = buildDiscoverQuery(request, sqlBusinessDate, hasLat);
        DailyStatusSnapshot statusSnapshot = null;
        if (Boolean.TRUE.equals(request.getOpenNow())) {
            statusSnapshot = loadDailyStatusSnapshot(null, businessDate, businessTime);
            List<Long> openNowIds = statusSnapshot.currentOpen.entrySet().stream()
                    .filter(entry -> Boolean.TRUE.equals(entry.getValue()))
                    .map(Map.Entry::getKey).collect(Collectors.toList());
            if (openNowIds.isEmpty()) query.eq(Stall::getId, -1L);
            else query.in(Stall::getId, openNowIds);
        }
        Page<Stall> pageInfo = PageHelper.startPage(page, limit);
        List<Stall> stalls = stallDao.selectList(query);
        if (stalls == null || stalls.isEmpty()) {
            return CommonPage.copyPageInfo(pageInfo, Collections.emptyList());
        }
        if (statusSnapshot == null) {
            List<Long> ids = stalls.stream().map(Stall::getId).collect(Collectors.toList());
            statusSnapshot = loadDailyStatusSnapshot(ids, businessDate, businessTime);
        }
        List<StallPublicResponse> responses = new java.util.ArrayList<>(stalls.size());
        for (Stall stall : stalls) {
            StallPublicResponse response = new StallPublicResponse();
            BeanUtils.copyProperties(stall, response);
            responses.add(response);
        }
        attachRegionNames(responses, stalls);
        attachDailyStatus(responses, stalls, statusSnapshot);
        return CommonPage.copyPageInfo(pageInfo, responses);
    }

    private LambdaQueryWrapper<Stall> buildDiscoverQuery(StallSearchRequest request, java.sql.Date businessDate, boolean hasLat) {
        LambdaQueryWrapper<Stall> query = Wrappers.lambdaQuery();
        query.eq(Stall::getAuditStatus, 1)
                .eq(Stall::getIsDeleted, false)
                .eq(StringUtils.isNotBlank(request.getCityCode()), Stall::getCityCode, request.getCityCode())
                .eq(StringUtils.isNotBlank(request.getDistrictCode()), Stall::getDistrictCode, request.getDistrictCode())
                .eq(request.getCategoryId() != null, Stall::getCategoryId, request.getCategoryId())
                .and(StringUtils.isNotBlank(request.getKeyword()), wrapper -> wrapper
                        .like(Stall::getName, request.getKeyword())
                        .or().like(Stall::getDescription, request.getKeyword()));
        if (hasLat) {
            query.isNotNull(Stall::getLatitude).isNotNull(Stall::getLongitude);
            if (request.getRadiusMeters() != null) {
                query.apply("ST_Distance_Sphere(POINT(longitude, latitude), POINT({0}, {1})) <= {2}",
                        request.getLng().doubleValue(), request.getLat().doubleValue(), request.getRadiusMeters());
            }
        }
        if (hasLat && "distance".equals(request.getSort())) {
            // Coordinates are validated BigDecimals before becoming SQL literals; filter parameters remain bound.
            query.last("ORDER BY ST_Distance_Sphere(POINT(longitude, latitude), POINT("
                    + request.getLng().toPlainString() + ", " + request.getLat().toPlainString()
                    + ")) ASC, id DESC");
        } else {
            query.orderByDesc(Stall::getUpdateTime).orderByDesc(Stall::getId);
        }
        if (Boolean.TRUE.equals(request.getTodayOpen())) {
            query.apply("EXISTS (SELECT 1 FROM eb_tl_stall_daily_status daily "
                            + "WHERE daily.stall_id = eb_tl_stall.id AND daily.status_date = {0} AND daily.is_open = 1)",
                    businessDate);
        }
        return query;
    }

    private DailyStatusSnapshot loadDailyStatusSnapshot(Collection<Long> stallIds, LocalDate businessDate, LocalTime now) {
        if (stallIds != null && stallIds.isEmpty()) return new DailyStatusSnapshot();
        java.sql.Date today = java.sql.Date.valueOf(businessDate);
        java.sql.Date yesterday = java.sql.Date.valueOf(businessDate.minusDays(1));
        List<StallDailyStatus> rows = dailyStatusDao.selectList(Wrappers.<StallDailyStatus>lambdaQuery()
                .in(StallDailyStatus::getStatusDate, java.util.Arrays.asList(today, yesterday))
                .in(stallIds != null, StallDailyStatus::getStallId, stallIds));
        DailyStatusSnapshot snapshot = new DailyStatusSnapshot();
        for (StallDailyStatus status : rows) {
            if (today.equals(status.getStatusDate())) snapshot.todayStatuses.put(status.getStallId(), status);
            else if (yesterday.equals(status.getStatusDate())) snapshot.previousStatuses.put(status.getStallId(), status);
        }
        Set<Long> ids = new HashSet<>();
        if (stallIds != null) ids.addAll(stallIds);
        else {
            ids.addAll(snapshot.todayStatuses.keySet());
            ids.addAll(snapshot.previousStatuses.keySet());
        }
        for (Long stallId : ids) {
            snapshot.currentOpen.put(stallId, StallBusinessHours.currentOpen(
                    snapshot.todayStatuses.get(stallId), snapshot.previousStatuses.get(stallId), now));
        }
        return snapshot;
    }

    @Override
    public StallPublicResponse getPublished(Long id) {
        Stall stall = lambdaQuery()
                .eq(Stall::getId, id)
                .eq(Stall::getAuditStatus, 1)
                .eq(Stall::getIsDeleted, false)
                .one();
        if (stall == null) {
            return null;
        }
        StallPublicResponse response = new StallPublicResponse();
        BeanUtils.copyProperties(stall, response);
        attachRegionNames(Collections.singletonList(response), Collections.singletonList(stall));
        ZoneId businessZone = ZoneId.of("Asia/Shanghai");
        LocalDateTime businessNow = LocalDateTime.now(businessZone);
        LocalDate businessDate = businessNow.toLocalDate();
        DailyStatusSnapshot snapshot = loadDailyStatusSnapshot(Collections.singletonList(stall.getId()),
                businessDate, businessNow.toLocalTime());
        attachDailyStatus(Collections.singletonList(response), Collections.singletonList(stall), snapshot);
        return response;
    }

    private void attachRegionNames(List<StallPublicResponse> responses, List<Stall> stalls) {
        Map<Integer, String> names = regionNameResolver.resolve(stalls);
        for (int i = 0; i < stalls.size(); i++) {
            Stall stall = stalls.get(i);
            StallPublicResponse response = responses.get(i);
            response.setCityName(regionNameResolver.name(stall.getCityCode(), names));
            response.setDistrictName(regionNameResolver.name(stall.getDistrictCode(), names));
        }
    }

    private void attachDailyStatus(List<StallPublicResponse> responses, List<Stall> stalls, DailyStatusSnapshot snapshot) {
        if (stalls.isEmpty()) return;
        for (int i = 0; i < stalls.size(); i++) {
            StallDailyStatus status = snapshot.todayStatuses.get(stalls.get(i).getId());
            StallPublicResponse response = responses.get(i);
            response.setTodayStatusKnown(status != null);
            response.setOpenToday(status == null ? null : status.getIsOpen());
            response.setTodayHours(status == null ? null : status.getHours());
            response.setCurrentOpen(snapshot.currentOpen.get(stalls.get(i).getId()));
        }
    }

    private static class DailyStatusSnapshot {
        private final Map<Long, StallDailyStatus> todayStatuses = new HashMap<>();
        private final Map<Long, StallDailyStatus> previousStatuses = new HashMap<>();
        private final Map<Long, Boolean> currentOpen = new HashMap<>();
    }

    @Override
    public Long submit(StallSubmissionRequest request) {
        Integer userId = userService.getUserIdException();
        StallCategory category = stallCategoryDao.selectById(request.getCategoryId());
        if (category == null || !Boolean.TRUE.equals(category.getEnabled())) {
            throw new IllegalArgumentException("摊位分类不存在或已停用");
        }
        Stall stall = new Stall()
                .setName(request.getName().trim())
                .setCategoryId(request.getCategoryId())
                .setDescription(request.getDescription())
                .setAddress(request.getAddress())
                .setCityCode(request.getCityCode())
                .setDistrictCode(request.getDistrictCode())
                .setLongitude(request.getLongitude())
                .setLatitude(request.getLatitude())
                .setCoverImage(primaryImage(request.getImageUrls(), request.getCoverImage()))
                .setImageUrls(normalizedImageUrls(request.getImageUrls(), request.getCoverImage()))
                .setBusinessHours(request.getBusinessHours())
                .setContactPhone(request.getContactPhone())
                .setAuditStatus(0)
                .setSourceType("user_submission")
                .setCreatedBy(userId.longValue())
                .setIsDeleted(false);
        save(stall);
        return stall.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createByOperator(StallSubmissionRequest request) {
        Integer adminId = SecurityUtil.getLoginUserVo().getUser().getId();
        StallCategory category = stallCategoryDao.selectById(request.getCategoryId());
        if (category == null || !Boolean.TRUE.equals(category.getEnabled())) {
            throw new IllegalArgumentException("摊位分类不存在或已停用");
        }
        Stall stall = new Stall()
                .setName(request.getName().trim())
                .setCategoryId(request.getCategoryId())
                .setDescription(request.getDescription())
                .setAddress(request.getAddress())
                .setCityCode(request.getCityCode())
                .setDistrictCode(request.getDistrictCode())
                .setLongitude(request.getLongitude())
                .setLatitude(request.getLatitude())
                .setCoverImage(primaryImage(request.getImageUrls(), request.getCoverImage()))
                .setImageUrls(normalizedImageUrls(request.getImageUrls(), request.getCoverImage()))
                .setBusinessHours(request.getBusinessHours())
                .setContactPhone(request.getContactPhone())
                .setAuditStatus(0)
                .setSourceType("operator")
                .setCreatedBy(adminId.longValue())
                .setIsDeleted(false);
        save(stall);
        moderationEventService.recordAdmin("stall_submission", stall.getId(), null, null, 0,
                "operator_create", "平台录入，等待摊位审核");
        return stall.getId();
    }

    private String normalizedImageUrls(String imageUrls, String coverImage) {
        if (StringUtils.isNotBlank(imageUrls)) {
            String[] images = imageUrls.split(",", -1);
            if (images.length > 9) throw new IllegalArgumentException("摊位最多上传 9 张图片");
            for (String image : images) {
                if (StringUtils.isBlank(image)) throw new IllegalArgumentException("图片地址不能为空");
            }
            return java.util.Arrays.stream(images).map(String::trim).collect(Collectors.joining(","));
        }
        return StringUtils.trimToNull(coverImage);
    }

    private String primaryImage(String imageUrls, String coverImage) {
        if (StringUtils.isNotBlank(imageUrls)) return imageUrls.split(",", -1)[0].trim();
        return StringUtils.trimToNull(coverImage);
    }

    @Override
    public PageInfo<Stall> getPending(PageParamRequest request) {
        Page<Stall> pageInfo = PageHelper.startPage(request.getPage(), request.getLimit());
        List<Stall> pending = stallDao.selectList(Wrappers.<Stall>lambdaQuery()
                .eq(Stall::getAuditStatus, 0)
                .eq(Stall::getIsDeleted, false)
                .orderByAsc(Stall::getCreateTime)
                .orderByAsc(Stall::getId));
        if (pending == null || pending.isEmpty()) {
            return CommonPage.copyPageInfo(pageInfo, Collections.emptyList());
        }
        return CommonPage.copyPageInfo(pageInfo, pending);
    }

    @Override
    public PageInfo<Stall> manage(PageParamRequest page, String keyword, String cityCode, String districtCode,
                                  Integer categoryId, Integer auditStatus) {
        if (auditStatus != null && auditStatus != 1 && auditStatus != 3) {
            throw new IllegalArgumentException("摊位维护仅支持已发布或已隐藏状态");
        }
        if (StringUtils.isNotBlank(keyword) && keyword.trim().length() > 120) {
            throw new IllegalArgumentException("名称筛选最多120字");
        }
        Page<Stall> paging = PageHelper.startPage(page.getPage(), page.getLimit());
        LambdaQueryWrapper<Stall> query = Wrappers.<Stall>lambdaQuery()
                .eq(Stall::getIsDeleted, false)
                .eq(auditStatus != null, Stall::getAuditStatus, auditStatus)
                .in(auditStatus == null, Stall::getAuditStatus, java.util.Arrays.asList(1, 3))
                .eq(StringUtils.isNotBlank(cityCode), Stall::getCityCode, cityCode)
                .eq(StringUtils.isNotBlank(districtCode), Stall::getDistrictCode, districtCode)
                .eq(categoryId != null, Stall::getCategoryId, categoryId)
                .and(StringUtils.isNotBlank(keyword), group -> group.like(Stall::getName, keyword.trim())
                        .or().like(Stall::getAddress, keyword.trim()))
                .orderByDesc(Stall::getUpdateTime).orderByDesc(Stall::getId);
        List<Stall> rows = stallDao.selectList(query);
        return CommonPage.copyPageInfo(paging, rows == null ? Collections.emptyList() : rows);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setManagedVisibility(Long id, boolean hide, StallVisibilityRequest request) {
        String reason = request == null ? null : StringUtils.trimToNull(request.getReason());
        if (reason == null) throw new IllegalArgumentException("请填写隐藏或恢复理由");
        if (reason.length() > 500) throw new IllegalArgumentException("理由最多500字");
        Integer adminId = SecurityUtil.getLoginUserVo().getUser().getId();
        int fromStatus = hide ? 1 : 3;
        int toStatus = hide ? 3 : 1;
        LambdaUpdateWrapper<Stall> update = Wrappers.<Stall>lambdaUpdate()
                .eq(Stall::getId, id)
                .eq(Stall::getAuditStatus, fromStatus)
                .eq(Stall::getIsDeleted, false)
                .set(Stall::getAuditStatus, toStatus)
                .set(Stall::getAuditNote, reason)
                .set(Stall::getAuditedBy, adminId)
                .set(Stall::getAuditTime, new Date());
        if (stallDao.update(null, update) != 1) {
            throw new IllegalStateException(hide ? "仅可隐藏已发布摊位" : "仅可恢复已隐藏摊位");
        }
        moderationEventService.recordAdmin("stall", id, null, fromStatus, toStatus,
                hide ? "hide" : "restore", reason);
    }

    @Override
    public List<StallDuplicateCandidateResponse> findDuplicateCandidates(StallDuplicateSearchRequest request) {
        String name = StringUtils.trimToNull(request.getName());
        String address = StringUtils.trimToNull(request.getAddress());
        boolean hasLatitude = request.getLat() != null;
        boolean hasLongitude = request.getLng() != null;
        if (hasLatitude != hasLongitude) throw new IllegalArgumentException("经纬度需要同时提供");
        if (name == null && address == null && !hasLatitude) {
            throw new IllegalArgumentException("请填写名称、地址或经纬度后查重");
        }
        LambdaQueryWrapper<Stall> query = Wrappers.<Stall>lambdaQuery()
                .eq(Stall::getIsDeleted, false)
                .in(Stall::getAuditStatus, java.util.Arrays.asList(0, 1, 3))
                .eq(StringUtils.isNotBlank(request.getCityCode()), Stall::getCityCode, request.getCityCode())
                .eq(StringUtils.isNotBlank(request.getDistrictCode()), Stall::getDistrictCode, request.getDistrictCode())
                .ne(request.getExcludeId() != null, Stall::getId, request.getExcludeId())
                .and(group -> {
                    boolean hasText = name != null || address != null;
                    if (name != null) group.like(Stall::getName, name);
                    if (address != null) {
                        if (name != null) group.or();
                        group.like(Stall::getAddress, address);
                    }
                    if (hasLatitude) {
                        if (hasText) group.or();
                        group.apply("ST_Distance_Sphere(POINT(longitude, latitude), POINT({0}, {1})) <= 300",
                                request.getLng(), request.getLat());
                    }
                })
                .orderByDesc(Stall::getUpdateTime).orderByDesc(Stall::getId)
                .last("LIMIT 10");
        List<Stall> candidates = stallDao.selectList(query);
        List<StallDuplicateCandidateResponse> result = new java.util.ArrayList<>();
        for (Stall stall : candidates) {
            StallDuplicateCandidateResponse response = new StallDuplicateCandidateResponse();
            response.setId(stall.getId());
            response.setName(stall.getName());
            response.setAddress(stall.getAddress());
            response.setCityCode(stall.getCityCode());
            response.setDistrictCode(stall.getDistrictCode());
            response.setCoverImage(stall.getCoverImage());
            response.setAuditStatus(stall.getAuditStatus());
            response.setSourceType(stall.getSourceType());
            result.add(response);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void review(Long id, boolean approve, String note) {
        Integer adminId = SecurityUtil.getLoginUserVo().getUser().getId();
        LambdaUpdateWrapper<Stall> update = Wrappers.<Stall>lambdaUpdate()
                .eq(Stall::getId, id)
                .eq(Stall::getAuditStatus, 0)
                .eq(Stall::getIsDeleted, false)
                .set(Stall::getAuditStatus, approve ? 1 : 2)
                .set(Stall::getAuditedBy, adminId)
                .set(Stall::getAuditNote, note)
                .set(Stall::getAuditTime, new Date());
        if (stallDao.update(null, update) != 1) {
            throw new IllegalStateException("摊位记录不存在或已完成审核");
        }
        moderationEventService.recordAdmin("stall_submission", id, null, 0, approve ? 1 : 2,
                approve ? "approve" : "reject", note);
    }
}
