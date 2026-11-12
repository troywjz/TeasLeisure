package com.zbkj.service.service.tl;

import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.StallRevision;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.request.tl.StallRevisionRequest;
import com.zbkj.common.response.tl.StallRevisionSelfResponse;

public interface StallRevisionService {
    Long submit(Long stallId, StallRevisionRequest request);
    PageInfo<StallRevisionSelfResponse> mine(PageParamRequest page);
    PageInfo<StallRevision> pending(PageParamRequest page);
    void review(Long revisionId, boolean approve, String note);
}
