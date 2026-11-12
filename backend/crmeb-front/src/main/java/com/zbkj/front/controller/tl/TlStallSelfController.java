package com.zbkj.front.controller.tl;

import com.github.pagehelper.PageInfo;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.tl.StallSelfResponse;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.tl.TlStallSelfService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/front/tl/me/stalls")
@Tag(name = "我的茶余摊位")
public class TlStallSelfController {
    @Autowired private TlStallSelfService service;

    @GetMapping("/submissions")
    public CommonResult<CommonPage<StallSelfResponse>> submissions(@Validated PageParamRequest page) {
        PageInfo<StallSelfResponse> result = service.submissions(page);
        return CommonResult.success(CommonPage.restPage(result));
    }

    @GetMapping("/managed")
    public CommonResult<CommonPage<StallSelfResponse>> managed(@Validated PageParamRequest page) {
        PageInfo<StallSelfResponse> result = service.managed(page);
        return CommonResult.success(CommonPage.restPage(result));
    }
}
