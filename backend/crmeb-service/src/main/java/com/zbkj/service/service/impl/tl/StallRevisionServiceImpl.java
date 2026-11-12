package com.zbkj.service.service.impl.tl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.Stall;
import com.zbkj.common.model.tl.StallRevision;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.request.tl.StallRevisionRequest;
import com.zbkj.common.response.tl.StallRevisionSelfResponse;
import com.zbkj.common.utils.SecurityUtil;
import com.zbkj.service.dao.tl.StallDao;
import com.zbkj.service.dao.tl.StallRevisionDao;
import com.zbkj.service.service.UserService;
import com.zbkj.service.service.tl.StallRevisionService;
import com.zbkj.service.service.tl.TlModerationEventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StallRevisionServiceImpl implements StallRevisionService {
    @Resource private StallRevisionDao revisionDao;
    @Resource private StallDao stallDao;
    @Resource private UserService userService;
    @Resource private TlModerationEventService moderationEventService;
    @Resource private StallRegionNameResolver regionNameResolver;

    @Override
    public Long submit(Long stallId, StallRevisionRequest request) {
        Integer userId = userService.getUserIdException();
        Stall stall = stallDao.selectById(stallId);
        if (stall == null || !userId.equals(stall.getOwnerUserId()) || !Integer.valueOf(1).equals(stall.getAuditStatus()) || Boolean.TRUE.equals(stall.getIsDeleted())) {
            throw new IllegalArgumentException("只有已认领且已发布的摊位可以申请修改");
        }
        Integer pendingCount = Math.toIntExact(revisionDao.selectCount(Wrappers.<StallRevision>lambdaQuery()
                .eq(StallRevision::getStallId, stallId).eq(StallRevision::getStatus, 0)));
        if (pendingCount != null && pendingCount > 0) throw new IllegalStateException("该摊位已有待审核的资料修改");
        StallRevision revision = new StallRevision().setStallId(stallId).setEditorUserId(userId)
                .setPendingKey(stallId).setName(request.getName().trim()).setCategoryId(request.getCategoryId())
                .setDescription(request.getDescription()).setAddress(request.getAddress())
                .setCityCode(request.getCityCode()).setDistrictCode(request.getDistrictCode())
                .setLongitude(request.getLongitude()).setLatitude(request.getLatitude())
                .setCoverImage(primaryImage(request.getImageUrls(), request.getCoverImage()))
                .setImageUrls(normalizedImageUrls(request.getImageUrls(), request.getCoverImage()))
                .setBusinessHours(request.getBusinessHours())
                .setContactPhone(request.getContactPhone()).setStatus(0).setCreateTime(new Date());
        try { revisionDao.insert(revision); }
        catch (org.springframework.dao.DuplicateKeyException duplicate) { throw new IllegalStateException("该摊位已有待审核的资料修改"); }
        return revision.getId();
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
    public PageInfo<StallRevisionSelfResponse> mine(PageParamRequest page) {
        Integer userId = userService.getUserIdException();
        Page<StallRevision> paging = PageHelper.startPage(page.getPage(), page.getLimit());
        List<StallRevision> rows = revisionDao.selectList(Wrappers.<StallRevision>lambdaQuery()
                .eq(StallRevision::getEditorUserId, userId)
                .orderByDesc(StallRevision::getCreateTime).orderByDesc(StallRevision::getId));
        if (rows == null || rows.isEmpty()) {
            return CommonPage.copyPageInfo(paging, Collections.emptyList());
        }
        List<Long> stallIds = rows.stream().map(StallRevision::getStallId).distinct().collect(Collectors.toList());
        Map<Long, Stall> stalls = stallDao.selectBatchIds(stallIds).stream()
                .collect(Collectors.toMap(Stall::getId, Function.identity(), (first, ignored) -> first));
        Map<Integer, String> regionNames = regionNameResolver.resolve(stalls.values());
        List<StallRevisionSelfResponse> result = new ArrayList<>(rows.size());
        for (StallRevision revision : rows) {
            Stall stall = stalls.get(revision.getStallId());
            StallRevisionSelfResponse response = new StallRevisionSelfResponse()
                    .setId(revision.getId())
                    .setStallId(revision.getStallId())
                    .setStatus(revision.getStatus())
                    .setAuditNote(revision.getAuditNote())
                    .setImageUrls(revision.getImageUrls())
                    .setCreateTime(revision.getCreateTime())
                    .setAuditTime(revision.getAuditTime());
            if (stall != null && !Boolean.TRUE.equals(stall.getIsDeleted())) {
                response.setStallName(stall.getName())
                        .setAddress(stall.getAddress())
                        .setCoverImage(stall.getCoverImage())
                        .setCityCode(stall.getCityCode())
                        .setDistrictCode(stall.getDistrictCode())
                        .setCityName(regionNameResolver.name(stall.getCityCode(), regionNames))
                        .setDistrictName(regionNameResolver.name(stall.getDistrictCode(), regionNames));
            }
            result.add(response);
        }
        return CommonPage.copyPageInfo(paging, result);
    }

    @Override
    public PageInfo<StallRevision> pending(PageParamRequest page) {
        Page<StallRevision> paging = PageHelper.startPage(page.getPage(), page.getLimit());
        List<StallRevision> rows = revisionDao.selectList(Wrappers.<StallRevision>lambdaQuery()
                .eq(StallRevision::getStatus, 0).orderByAsc(StallRevision::getCreateTime));
        return CommonPage.copyPageInfo(paging, rows == null ? Collections.emptyList() : rows);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void review(Long revisionId, boolean approve, String note) {
        StallRevision revision = revisionDao.selectById(revisionId);
        if (revision == null || !Integer.valueOf(0).equals(revision.getStatus())) throw new IllegalStateException("资料修改申请不存在或已处理");
        Integer adminId = SecurityUtil.getLoginUserVo().getUser().getId();
        if (approve) {
            Stall current = stallDao.selectById(revision.getStallId());
            if (current == null || !revision.getEditorUserId().equals(current.getOwnerUserId()) || !Integer.valueOf(1).equals(current.getAuditStatus())) {
                throw new IllegalStateException("摊位权属或状态已变化，资料修改需重新核对");
            }
            Stall updated = new Stall().setId(current.getId()).setName(revision.getName())
                    .setCategoryId(revision.getCategoryId()).setDescription(revision.getDescription())
                    .setAddress(revision.getAddress()).setCityCode(revision.getCityCode())
                    .setDistrictCode(revision.getDistrictCode()).setLongitude(revision.getLongitude())
                    .setLatitude(revision.getLatitude()).setCoverImage(revision.getCoverImage())
                    .setImageUrls(revision.getImageUrls())
                    .setBusinessHours(revision.getBusinessHours()).setContactPhone(revision.getContactPhone());
            stallDao.updateById(updated);
        }
        int changed = revisionDao.update(null, Wrappers.<StallRevision>lambdaUpdate().eq(StallRevision::getId, revisionId)
                .eq(StallRevision::getStatus, 0).set(StallRevision::getStatus, approve ? 1 : 2).set(StallRevision::getPendingKey, null)
                .set(StallRevision::getAuditedBy, adminId).set(StallRevision::getAuditNote, note).set(StallRevision::getAuditTime, new Date()));
        if (changed != 1) throw new IllegalStateException("资料修改申请已由其他管理员处理");
        moderationEventService.recordAdmin("stall_revision", revisionId, revision.getStallId(), 0,
                approve ? 1 : 2, approve ? "approve" : "reject", note);
    }
}
