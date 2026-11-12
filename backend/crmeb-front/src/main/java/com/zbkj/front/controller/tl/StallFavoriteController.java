package com.zbkj.front.controller.tl;

import com.github.pagehelper.PageInfo;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.request.tl.StallFavoriteRequest;
import com.zbkj.common.response.tl.StallPublicResponse;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.tl.StallFavoriteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/front/tl/favorites/stalls")
@Tag(name = "茶余地摊收藏")
public class StallFavoriteController {
    @Autowired private StallFavoriteService service;

    @PostMapping
    @Operation(summary = "收藏摊位，重复请求幂等")
    public CommonResult<String> add(@RequestBody @Validated StallFavoriteRequest request) {
        service.add(request.getStallId()); return CommonResult.success();
    }

    @DeleteMapping("/{stallId}")
    @Operation(summary = "取消收藏")
    public CommonResult<String> remove(@PathVariable Long stallId) {
        service.remove(stallId); return CommonResult.success();
    }

    @GetMapping
    @Operation(summary = "查看我的收藏摊位")
    public CommonResult<CommonPage<StallPublicResponse>> list(@Validated PageParamRequest page) {
        PageInfo<StallPublicResponse> result = service.list(page);
        return CommonResult.success(CommonPage.restPage(result));
    }
}
