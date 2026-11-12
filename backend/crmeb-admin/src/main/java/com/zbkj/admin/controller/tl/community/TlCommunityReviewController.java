package com.zbkj.admin.controller.tl.community;

import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.community.TlComment;
import com.zbkj.common.model.tl.community.TlPost;
import com.zbkj.common.annotation.LogControllerAnnotation;
import com.zbkj.common.enums.MethodType;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.tl.community.TlCommunityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/tl/community/review")
@Tag(name = "茶余社区内容审核")
@PreAuthorize("hasAuthority('tl:community:review')")
public class TlCommunityReviewController {
    @Autowired private TlCommunityService service;

    @GetMapping("/posts") @Operation(summary = "待审核动态")
    public CommonResult<CommonPage<TlPost>> posts(@Validated PageParamRequest page) {
        PageInfo<TlPost> rows = service.pendingPosts(page);
        return CommonResult.success(CommonPage.restPage(rows));
    }

    @PostMapping("/posts/{id}/approve")
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "茶余动态审核通过")
    public CommonResult<String> approvePost(@PathVariable Long id, @RequestParam(required = false) String note) {
        service.reviewPost(id, true, note); return CommonResult.success();
    }
    @PostMapping("/posts/{id}/reject")
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "茶余动态审核驳回")
    public CommonResult<String> rejectPost(@PathVariable Long id, @RequestParam(required = false) String note) {
        service.reviewPost(id, false, note); return CommonResult.success();
    }

    @GetMapping("/comments") @Operation(summary = "待审核评论")
    public CommonResult<CommonPage<TlComment>> comments(@Validated PageParamRequest page) {
        PageInfo<TlComment> rows = service.pendingComments(page);
        return CommonResult.success(CommonPage.restPage(rows));
    }

    @PostMapping("/comments/{id}/approve")
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "茶余评论审核通过")
    public CommonResult<String> approveComment(@PathVariable Long id, @RequestParam(required = false) String note) {
        service.reviewComment(id, true, note); return CommonResult.success();
    }
    @PostMapping("/comments/{id}/reject")
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "茶余评论审核驳回")
    public CommonResult<String> rejectComment(@PathVariable Long id, @RequestParam(required = false) String note) {
        service.reviewComment(id, false, note); return CommonResult.success();
    }
}
