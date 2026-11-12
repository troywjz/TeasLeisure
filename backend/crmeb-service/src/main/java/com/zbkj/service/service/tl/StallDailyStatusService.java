package com.zbkj.service.service.tl;

import com.zbkj.common.model.tl.StallDailyStatus;
import com.zbkj.common.request.tl.StallTodayStatusRequest;

public interface StallDailyStatusService {
    StallDailyStatus getToday(Long stallId);
    void updateToday(Long stallId, StallTodayStatusRequest request);
}
