package com.zbkj.admin.controller.tl.conversation;

import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.conversation.TlMessageReport;
import com.zbkj.common.annotation.LogControllerAnnotation;
import com.zbkj.common.enums.MethodType;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.tl.conversation.TlConversationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/tl/message-reports")
@Tag(name = "茶余私信举报处理")
public class TlMessageReportController {
    @Autowired private TlConversationService service;

    @GetMapping
    @PreAuthorize("hasAuthority('tl:message:report')")
    @Operation(summary = "待处理私信举报")
    public CommonResult<CommonPage<TlMessageReport>> pending(@Validated PageParamRequest page) {
        PageInfo<TlMessageReport> result = service.pendingReports(page);
        return CommonResult.success(CommonPage.restPage(result));
    }

    @PostMapping("/{id}/resolve")
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "茶余私信举报处理")
    @PreAuthorize("hasAuthority('tl:message:report')")
    public CommonResult<String> resolve(@PathVariable Long id, @RequestParam(required = false) String note) {
        service.handleReport(id, true, note); return CommonResult.success();
    }

    @PostMapping("/{id}/dismiss")
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "茶余私信举报驳回")
    @PreAuthorize("hasAuthority('tl:message:report')")
    public CommonResult<String> dismiss(@PathVariable Long id, @RequestParam String note) {
        service.handleReport(id, false, note); return CommonResult.success();
    }
}
