package com.zbkj.service.service.tl;

import com.github.pagehelper.PageInfo;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.tl.StallPublicResponse;

public interface StallFavoriteService {
    void add(Long stallId);
    void remove(Long stallId);
    PageInfo<StallPublicResponse> list(PageParamRequest page);
}
