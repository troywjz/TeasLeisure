package com.zbkj.service.service.impl.tl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zbkj.common.model.tl.Stall;
import com.zbkj.common.model.tl.StallDailyStatus;
import com.zbkj.common.request.tl.StallTodayStatusRequest;
import com.zbkj.service.dao.tl.StallDailyStatusDao;
import com.zbkj.service.dao.tl.StallDao;
import com.zbkj.service.service.UserService;
import com.zbkj.service.service.tl.StallDailyStatusService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

@Service
public class StallDailyStatusServiceImpl implements StallDailyStatusService {
    private static final ZoneId BUSINESS_ZONE = ZoneId.of("Asia/Shanghai");

    @Resource private StallDailyStatusDao statusDao;
    @Resource private StallDao stallDao;
    @Resource private UserService userService;

    @Override
    public StallDailyStatus getToday(Long stallId) {
        return statusDao.selectOne(Wrappers.<StallDailyStatus>lambdaQuery()
                .eq(StallDailyStatus::getStallId, stallId)
                .eq(StallDailyStatus::getStatusDate, today()));
    }

    @Override
    public void updateToday(Long stallId, StallTodayStatusRequest request) {
        Integer userId = userService.getUserIdException();
        Stall stall = stallDao.selectById(stallId);
        if (stall == null || !Integer.valueOf(1).equals(stall.getAuditStatus())
                || !Boolean.FALSE.equals(stall.getIsDeleted())
                || !Objects.equals(stall.getOwnerUserId(), userId)) {
            throw new IllegalArgumentException("仅可更新本人已认领且已发布的摊位状态");
        }
        String hours = request.getHours() == null ? null : request.getHours().trim();
        if (Boolean.TRUE.equals(request.getIsOpen()) && (hours == null || hours.isEmpty())) {
            throw new IllegalArgumentException("出摊时请填写今天的预计时段");
        }
        if (Boolean.TRUE.equals(request.getIsOpen()) && !StallBusinessHours.isValid(hours)) {
            throw new IllegalArgumentException("时段格式须为 HH:mm-HH:mm，例如 17:00-22:00");
        }
        statusDao.upsertToday(stallId, today(), request.getIsOpen(), hours, userId);
    }

    private Date today() {
        return Date.valueOf(LocalDate.now(BUSINESS_ZONE));
    }
}
