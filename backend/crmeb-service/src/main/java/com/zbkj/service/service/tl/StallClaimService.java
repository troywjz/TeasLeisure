package com.zbkj.service.service.tl;

import com.zbkj.common.model.tl.StallClaim;
import com.zbkj.common.request.tl.StallClaimRequest;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.tl.StallClaimSelfResponse;
import com.github.pagehelper.PageInfo;
import java.util.List;

public interface StallClaimService {
    Long apply(StallClaimRequest request);
    PageInfo<StallClaimSelfResponse> mine(PageParamRequest page);
    List<StallClaim> pending();
    void review(Long id, boolean approve, String note);
}
