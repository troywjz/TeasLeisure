package com.zbkj.front.controller.tl;

import com.zbkj.common.request.tl.StallSubmissionRequest;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.tl.StallService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/front/tl/stall-submissions")
@Tag(name = "茶余地摊投稿")
public class StallSubmissionFrontController {

    @Autowired
    private StallService stallService;

    @PostMapping
    @Operation(summary = "个人用户提交地摊信息，提交后进入审核")
    public CommonResult<Long> submit(@RequestBody @Validated StallSubmissionRequest request) {
        return CommonResult.success(stallService.submit(request));
    }
}
