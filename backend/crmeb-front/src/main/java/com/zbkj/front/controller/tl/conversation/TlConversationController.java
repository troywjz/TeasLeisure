package com.zbkj.front.controller.tl.conversation;

import com.github.pagehelper.PageInfo;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.request.tl.conversation.MessageReportRequest;
import com.zbkj.common.request.tl.conversation.MessageSendRequest;
import com.zbkj.common.response.tl.conversation.TlConversationResponse;
import com.zbkj.common.response.tl.conversation.TlMessageResponse;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.tl.conversation.TlConversationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/front/tl/conversations")
@Tag(name = "茶余私信")
public class TlConversationController {
    @Autowired private TlConversationService service;

    @PostMapping
    @Operation(summary = "联系已认证摊主并创建会话")
    public CommonResult<Long> open(@RequestParam Long stallId) { return CommonResult.success(service.open(stallId)); }

    @GetMapping
    @Operation(summary = "查看我的会话")
    public CommonResult<List<TlConversationResponse>> list() { return CommonResult.success(service.listMine()); }

    @GetMapping("/unread-count")
    @Operation(summary = "获取我的私信未读总数")
    public CommonResult<Integer> unreadCount() { return CommonResult.success(service.unreadCount()); }

    @GetMapping("/{id}/messages")
    @Operation(summary = "分页查看会话消息")
    public CommonResult<CommonPage<TlMessageResponse>> messages(@PathVariable Long id, @Validated PageParamRequest page) {
        PageInfo<TlMessageResponse> result = service.messages(id, page);
        return CommonResult.success(CommonPage.restPage(result));
    }

    @PostMapping("/{id}/messages")
    @Operation(summary = "发送会话消息")
    public CommonResult<Long> send(@PathVariable Long id, @RequestBody @Validated MessageSendRequest request) {
        return CommonResult.success(service.send(id, request));
    }

    @PostMapping("/{id}/read")
    public CommonResult<String> read(@PathVariable Long id, @RequestParam Long lastMessageId) {
        service.markRead(id, lastMessageId); return CommonResult.success();
    }

    @PostMapping("/{id}/block")
    public CommonResult<String> block(@PathVariable Long id, @RequestParam(defaultValue = "true") Boolean blocked) {
        service.block(id, blocked); return CommonResult.success();
    }

    @GetMapping("/{id}/block")
    @Operation(summary = "查询当前会话中的屏蔽状态")
    public CommonResult<Boolean> blockStatus(@PathVariable Long id) {
        return CommonResult.success(service.isBlockedInConversation(id));
    }

    @PostMapping("/reports")
    @Operation(summary = "举报会话或消息")
    public CommonResult<Long> report(@RequestBody @Validated MessageReportRequest request) {
        return CommonResult.success(service.report(request));
    }
}
