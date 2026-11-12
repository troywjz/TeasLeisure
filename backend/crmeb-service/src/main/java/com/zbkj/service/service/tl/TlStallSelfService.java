package com.zbkj.service.service.tl;

import com.github.pagehelper.PageInfo;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.tl.StallSelfResponse;

public interface TlStallSelfService {
    PageInfo<StallSelfResponse> submissions(PageParamRequest page);
    PageInfo<StallSelfResponse> managed(PageParamRequest page);
}
