package com.zbkj.service.service.tl;

import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.TlModerationEvent;
import com.zbkj.common.request.PageParamRequest;

public interface TlModerationEventService {
    void recordAdmin(String targetType, Long targetId, Long relatedId, Integer fromStatus,
                     Integer toStatus, String action, String reason);

    void recordActor(String targetType, Long targetId, Long relatedId, Integer fromStatus,
                     Integer toStatus, String action, String reason, String actorType,
                     Integer actorId, String actorAccount);

    PageInfo<TlModerationEvent> list(PageParamRequest page, String targetType, Long targetId);
}
