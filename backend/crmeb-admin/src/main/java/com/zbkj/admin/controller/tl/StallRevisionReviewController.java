package com.zbkj.admin.controller.tl;

import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.StallRevision;
import com.zbkj.common.annotation.LogControllerAnnotation;
import com.zbkj.common.enums.MethodType;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.tl.StallRevisionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/tl/stall-revisions")
@Tag(name = "茶余摊位资料修改审核")
public class StallRevisionReviewController {
    @Autowired private StallRevisionService service;

    @GetMapping
    @PreAuthorize("hasAuthority('tl:stall:revision:review')")
    @Operation(summary = "待审核摊位资料修改")
    public CommonResult<CommonPage<StallRevision>> pending(@Validated PageParamRequest page) {
        PageInfo<StallRevision> result = service.pending(page);
        return CommonResult.success(CommonPage.restPage(result));
    }

    @PostMapping("/{id}/approve")
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "茶余摊位资料修订审核通过")
    @PreAuthorize("hasAuthority('tl:stall:revision:review')")
    public CommonResult<String> approve(@PathVariable Long id, @RequestParam(required = false) String note) {
        service.review(id, true, note); return CommonResult.success();
    }

    @PostMapping("/{id}/reject")
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "茶余摊位资料修订审核驳回")
    @PreAuthorize("hasAuthority('tl:stall:revision:review')")
    public CommonResult<String> reject(@PathVariable Long id, @RequestParam String note) {
        service.review(id, false, note); return CommonResult.success();
    }
}
