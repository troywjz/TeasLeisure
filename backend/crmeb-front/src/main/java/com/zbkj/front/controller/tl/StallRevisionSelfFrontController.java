package com.zbkj.front.controller.tl;

import com.github.pagehelper.PageInfo;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.tl.StallRevisionSelfResponse;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.tl.StallRevisionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/front/tl/me/stalls/revisions")
@Tag(name = "我的摊位资料修订")
public class StallRevisionSelfFrontController {
    @Autowired private StallRevisionService service;

    @GetMapping
    @Operation(summary = "分页查看本人提交的摊位资料修订")
    public CommonResult<CommonPage<StallRevisionSelfResponse>> mine(@Validated PageParamRequest page) {
        PageInfo<StallRevisionSelfResponse> result = service.mine(page);
        return CommonResult.success(CommonPage.restPage(result));
    }
}
