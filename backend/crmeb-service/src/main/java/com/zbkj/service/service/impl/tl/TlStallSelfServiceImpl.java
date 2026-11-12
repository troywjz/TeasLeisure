package com.zbkj.service.service.impl.tl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.Stall;
import com.zbkj.common.model.tl.StallDailyStatus;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.tl.StallSelfResponse;
import com.zbkj.service.dao.tl.StallDao;
import com.zbkj.service.dao.tl.StallDailyStatusDao;
import com.zbkj.service.service.UserService;
import com.zbkj.service.service.tl.TlStallSelfService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class TlStallSelfServiceImpl implements TlStallSelfService {
    @Resource private StallDao stallDao;
    @Resource private StallDailyStatusDao dailyStatusDao;
    @Resource private UserService userService;
    @Resource private StallRegionNameResolver regionNameResolver;

    @Override public PageInfo<StallSelfResponse> submissions(PageParamRequest page) {
        Integer userId = userService.getUserIdException();
        Page<Stall> paging = PageHelper.startPage(page.getPage(), page.getLimit());
        List<Stall> rows = stallDao.selectList(Wrappers.<Stall>lambdaQuery()
                .eq(Stall::getCreatedBy, userId.longValue()).eq(Stall::getIsDeleted, false)
                .orderByDesc(Stall::getCreateTime).orderByDesc(Stall::getId));
        return CommonPage.copyPageInfo(paging, toResponses(rows, userId));
    }

    @Override public PageInfo<StallSelfResponse> managed(PageParamRequest page) {
        Integer userId = userService.getUserIdException();
        Page<Stall> paging = PageHelper.startPage(page.getPage(), page.getLimit());
        List<Stall> rows = stallDao.selectList(Wrappers.<Stall>lambdaQuery()
                .eq(Stall::getOwnerUserId, userId).eq(Stall::getIsDeleted, false)
                .orderByDesc(Stall::getUpdateTime).orderByDesc(Stall::getId));
        List<StallSelfResponse> result = toResponses(rows, userId);
        attachTodayStatus(rows, result);
        return CommonPage.copyPageInfo(paging, result);
    }

    private List<StallSelfResponse> toResponses(List<Stall> rows, Integer userId) {
        if (rows == null || rows.isEmpty()) return Collections.emptyList();
        Map<Integer, String> regionNames = regionNameResolver.resolve(rows);
        List<StallSelfResponse> result = new ArrayList<>(rows.size());
        for (Stall row : rows) {
            StallSelfResponse response = new StallSelfResponse();
            BeanUtils.copyProperties(row, response);
            response.setCityName(regionNameResolver.name(row.getCityCode(), regionNames));
            response.setDistrictName(regionNameResolver.name(row.getDistrictCode(), regionNames));
            response.setClaimedByMe(userId != null && userId.equals(row.getOwnerUserId()));
            response.setClaimable(Integer.valueOf(1).equals(row.getAuditStatus())
                    && !Boolean.TRUE.equals(row.getIsDeleted()) && row.getOwnerUserId() == null);
            result.add(response);
        }
        return result;
    }

    private void attachTodayStatus(List<Stall> rows, List<StallSelfResponse> responses) {
        if (rows == null || rows.isEmpty()) return;
        java.sql.Date businessDate = java.sql.Date.valueOf(LocalDate.now(ZoneId.of("Asia/Shanghai")));
        List<Long> ids = rows.stream().map(Stall::getId).collect(Collectors.toList());
        Map<Long, StallDailyStatus> statusMap = dailyStatusDao.selectList(Wrappers.<StallDailyStatus>lambdaQuery()
                .in(StallDailyStatus::getStallId, ids).eq(StallDailyStatus::getStatusDate, businessDate))
                .stream().collect(Collectors.toMap(StallDailyStatus::getStallId, status -> status));
        for (int i = 0; i < rows.size(); i++) {
            StallDailyStatus status = statusMap.get(rows.get(i).getId());
            StallSelfResponse response = responses.get(i);
            response.setTodayStatusKnown(status != null);
            response.setOpenToday(status == null ? null : status.getIsOpen());
            response.setTodayHours(status == null ? null : status.getHours());
        }
    }
}
