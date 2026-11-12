package com.zbkj.front.controller.tl;

import com.zbkj.common.request.tl.StallRevisionRequest;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.tl.StallRevisionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/front/tl/stalls")
@Tag(name = "茶余摊位资料修改")
public class StallRevisionFrontController {
    @Autowired private StallRevisionService service;

    @PostMapping("/{id}/revisions")
    @Operation(summary = "摊主提交摊位资料修改申请，审核期间现有资料继续展示")
    public CommonResult<Long> submit(@PathVariable Long id, @RequestBody @Validated StallRevisionRequest request) {
        return CommonResult.success(service.submit(id, request));
    }
}
