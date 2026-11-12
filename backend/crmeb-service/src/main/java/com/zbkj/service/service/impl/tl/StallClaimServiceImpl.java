package com.zbkj.service.service.impl.tl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.Stall;
import com.zbkj.common.model.tl.StallClaim;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.tl.StallClaimSelfResponse;
import com.zbkj.common.utils.SecurityUtil;
import com.zbkj.service.dao.tl.StallClaimDao;
import com.zbkj.service.dao.tl.StallDao;
import com.zbkj.service.service.UserService;
import com.zbkj.service.service.tl.StallClaimService;
import com.zbkj.service.service.tl.TlModerationEventService;
import com.zbkj.common.request.tl.StallClaimRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StallClaimServiceImpl implements StallClaimService {
    @Resource private StallClaimDao claimDao;
    @Resource private StallDao stallDao;
    @Resource private UserService userService;
    @Resource private TlModerationEventService moderationEventService;
    @Resource private StallRegionNameResolver regionNameResolver;

    @Override
    public Long apply(StallClaimRequest request) {
        Integer userId = userService.getUserIdException();
        Stall stall = stallDao.selectById(request.getStallId());
        if (stall == null || !Integer.valueOf(1).equals(stall.getAuditStatus()) || Boolean.TRUE.equals(stall.getIsDeleted()) || stall.getOwnerUserId() != null) {
            throw new IllegalArgumentException("摊位不存在或不可认领");
        }
        Integer duplicate = Math.toIntExact(claimDao.selectCount(Wrappers.<StallClaim>lambdaQuery()
                .eq(StallClaim::getStallId, stall.getId()).eq(StallClaim::getUserId, userId).eq(StallClaim::getStatus, 0)));
        if (duplicate != null && duplicate > 0) throw new IllegalStateException("已有待处理的认领申请");
        String proofImageUrls = normalizeProofImageUrls(request.getProofImageUrls());
        StallClaim claim = new StallClaim().setStallId(stall.getId()).setUserId(userId)
                .setProof(request.getProof().trim()).setProofImageUrls(proofImageUrls).setStatus(0).setCreateTime(new Date());
        claimDao.insert(claim);
        return claim.getId();
    }

    private String normalizeProofImageUrls(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        String[] urls = value.split(",", -1);
        if (urls.length > 5) throw new IllegalArgumentException("现场照片最多上传 5 张");
        for (String url : urls) if (url.trim().isEmpty()) throw new IllegalArgumentException("现场照片地址不能为空");
        return java.util.Arrays.stream(urls).map(String::trim).collect(Collectors.joining(","));
    }

    @Override
    public PageInfo<StallClaimSelfResponse> mine(PageParamRequest page) {
        Integer userId = userService.getUserIdException();
        Page<StallClaim> paging = PageHelper.startPage(page.getPage(), page.getLimit());
        List<StallClaim> rows = claimDao.selectList(Wrappers.<StallClaim>lambdaQuery()
                .eq(StallClaim::getUserId, userId)
                .orderByDesc(StallClaim::getCreateTime).orderByDesc(StallClaim::getId));
        if (rows == null || rows.isEmpty()) {
            return CommonPage.copyPageInfo(paging, Collections.emptyList());
        }
        List<Long> stallIds = rows.stream().map(StallClaim::getStallId).distinct().collect(Collectors.toList());
        Map<Long, Stall> stalls = stallDao.selectBatchIds(stallIds).stream()
                .collect(Collectors.toMap(Stall::getId, Function.identity(), (first, ignored) -> first));
        Map<Integer, String> regionNames = regionNameResolver.resolve(stalls.values());
        List<StallClaimSelfResponse> result = new ArrayList<>(rows.size());
        for (StallClaim claim : rows) {
            Stall stall = stalls.get(claim.getStallId());
            StallClaimSelfResponse response = new StallClaimSelfResponse()
                    .setId(claim.getId())
                    .setStallId(claim.getStallId())
                    .setStatus(claim.getStatus())
                    .setAuditNote(claim.getAuditNote())
                    .setAuditTime(claim.getAuditTime())
                    .setCreateTime(claim.getCreateTime());
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
    public List<StallClaim> pending() {
        return claimDao.selectList(Wrappers.<StallClaim>lambdaQuery().eq(StallClaim::getStatus, 0).orderByAsc(StallClaim::getCreateTime));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void review(Long id, boolean approve, String note) {
        StallClaim claim = claimDao.selectById(id);
        if (claim == null || !Integer.valueOf(0).equals(claim.getStatus())) throw new IllegalStateException("认领申请不存在或已处理");
        if (approve) {
            int ownerUpdate = stallDao.update(null, Wrappers.<Stall>lambdaUpdate()
                    .eq(Stall::getId, claim.getStallId()).isNull(Stall::getOwnerUserId)
                    .set(Stall::getOwnerUserId, claim.getUserId()));
            if (ownerUpdate != 1) throw new IllegalStateException("摊位已有认领人或记录不可用，需人工处理权属变更");
        }
        LambdaUpdateWrapper<StallClaim> claimUpdate = Wrappers.<StallClaim>lambdaUpdate()
                .eq(StallClaim::getId, id).eq(StallClaim::getStatus, 0)
                .set(StallClaim::getStatus, approve ? 1 : 2)
                .set(StallClaim::getAuditedBy, SecurityUtil.getLoginUserVo().getUser().getId())
                .set(StallClaim::getAuditNote, note).set(StallClaim::getAuditTime, new Date());
        if (claimDao.update(null, claimUpdate) != 1) throw new IllegalStateException("认领申请已由其他管理员处理");
        moderationEventService.recordAdmin("stall_claim", claim.getId(), claim.getStallId(), 0,
                approve ? 1 : 2, approve ? "approve" : "reject", note);
    }
}
