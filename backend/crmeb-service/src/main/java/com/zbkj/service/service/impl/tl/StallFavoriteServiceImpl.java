package com.zbkj.service.service.impl.tl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.Stall;
import com.zbkj.common.model.tl.StallDailyStatus;
import com.zbkj.common.model.tl.StallFavorite;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.tl.StallPublicResponse;
import com.zbkj.service.dao.tl.StallDao;
import com.zbkj.service.dao.tl.StallFavoriteDao;
import com.zbkj.service.dao.tl.StallDailyStatusDao;
import com.zbkj.service.service.UserService;
import com.zbkj.service.service.tl.StallFavoriteService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.stream.Collectors;

@Service
public class StallFavoriteServiceImpl implements StallFavoriteService {
    @Resource private StallDao stallDao;
    @Resource private StallFavoriteDao favoriteDao;
    @Resource private StallDailyStatusDao dailyStatusDao;
    @Resource private UserService userService;
    @Resource private StallRegionNameResolver regionNameResolver;

    @Override
    public void add(Long stallId) {
        Integer userId = userService.getUserIdException();
        Integer visible = Math.toIntExact(stallDao.selectCount(Wrappers.<Stall>lambdaQuery().eq(Stall::getId, stallId)
                .eq(Stall::getAuditStatus, 1).eq(Stall::getIsDeleted, false)));
        if (visible == null || visible == 0) throw new IllegalArgumentException("摊位不存在或暂不可见");
        StallFavorite favorite = new StallFavorite().setStallId(stallId).setUserId(userId).setCreateTime(new Date());
        try { favoriteDao.insert(favorite); } catch (DuplicateKeyException ignored) { }
    }

    @Override
    public void remove(Long stallId) {
        Integer userId = userService.getUserIdException();
        favoriteDao.delete(Wrappers.<StallFavorite>lambdaQuery().eq(StallFavorite::getStallId, stallId)
                .eq(StallFavorite::getUserId, userId));
    }

    @Override
    public PageInfo<StallPublicResponse> list(PageParamRequest page) {
        Integer userId = userService.getUserIdException();
        Page<StallFavorite> paging = PageHelper.startPage(page.getPage(), page.getLimit());
        List<StallFavorite> favorites = favoriteDao.selectList(Wrappers.<StallFavorite>lambdaQuery()
                .eq(StallFavorite::getUserId, userId).orderByDesc(StallFavorite::getCreateTime));
        if (favorites == null || favorites.isEmpty()) return CommonPage.copyPageInfo(paging, Collections.emptyList());
        List<Long> ids = new ArrayList<>();
        for (StallFavorite favorite : favorites) ids.add(favorite.getStallId());
        List<Stall> stalls = stallDao.selectList(Wrappers.<Stall>lambdaQuery().in(Stall::getId, ids)
                .eq(Stall::getAuditStatus, 1).eq(Stall::getIsDeleted, false));
        if (stalls == null || stalls.isEmpty()) return CommonPage.copyPageInfo(paging, Collections.emptyList());
        Map<Integer, String> regionNames = regionNameResolver.resolve(stalls);
        ZoneId businessZone = ZoneId.of("Asia/Shanghai");
        LocalDateTime businessNow = LocalDateTime.now(businessZone);
        LocalDate businessDate = businessNow.toLocalDate();
        java.sql.Date today = java.sql.Date.valueOf(businessDate);
        java.sql.Date yesterday = java.sql.Date.valueOf(businessDate.minusDays(1));
        List<StallDailyStatus> dailyStatuses = dailyStatusDao.selectList(Wrappers.<StallDailyStatus>lambdaQuery()
                .in(StallDailyStatus::getStallId, stalls.stream().map(Stall::getId).collect(Collectors.toList()))
                .in(StallDailyStatus::getStatusDate, java.util.Arrays.asList(today, yesterday)));
        Map<Long, StallDailyStatus> todayStatusMap = new java.util.HashMap<>();
        Map<Long, StallDailyStatus> previousStatusMap = new java.util.HashMap<>();
        for (StallDailyStatus status : dailyStatuses) {
            if (today.equals(status.getStatusDate())) todayStatusMap.put(status.getStallId(), status);
            else if (yesterday.equals(status.getStatusDate())) previousStatusMap.put(status.getStallId(), status);
        }
        LocalTime now = businessNow.toLocalTime();
        List<StallPublicResponse> result = new ArrayList<>();
        for (Long id : ids) for (Stall stall : stalls) if (id.equals(stall.getId())) {
            StallPublicResponse response = new StallPublicResponse();
            BeanUtils.copyProperties(stall, response);
            response.setCityName(regionNameResolver.name(stall.getCityCode(), regionNames));
            response.setDistrictName(regionNameResolver.name(stall.getDistrictCode(), regionNames));
            StallDailyStatus todayStatus = todayStatusMap.get(stall.getId());
            response.setTodayStatusKnown(todayStatus != null);
            response.setOpenToday(todayStatus == null ? null : todayStatus.getIsOpen());
            response.setTodayHours(todayStatus == null ? null : todayStatus.getHours());
            response.setCurrentOpen(StallBusinessHours.currentOpen(todayStatus, previousStatusMap.get(stall.getId()), now));
            result.add(response);
            break;
        }
        return CommonPage.copyPageInfo(paging, result);
    }
}
