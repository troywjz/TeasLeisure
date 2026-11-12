package com.zbkj.front.controller.tl;

import com.zbkj.common.request.tl.StallClaimRequest;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.tl.StallClaimSelfResponse;
import com.zbkj.common.result.CommonResult;
import com.zbkj.common.page.CommonPage;
import com.github.pagehelper.PageInfo;
import com.zbkj.service.service.tl.StallClaimService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/front/tl/stall-claims")
@Tag(name = "茶余摊主认领")
public class StallClaimFrontController {
    @Autowired private StallClaimService claimService;

    @PostMapping
    @Operation(summary = "申请认领摊位")
    public CommonResult<Long> apply(@RequestBody @Validated StallClaimRequest request) {
        return CommonResult.success(claimService.apply(request));
    }

    @GetMapping("/mine")
    @Operation(summary = "分页查看我的摊位认领申请")
    public CommonResult<CommonPage<StallClaimSelfResponse>> mine(@Validated PageParamRequest page) {
        PageInfo<StallClaimSelfResponse> result = claimService.mine(page);
        return CommonResult.success(CommonPage.restPage(result));
    }
}
