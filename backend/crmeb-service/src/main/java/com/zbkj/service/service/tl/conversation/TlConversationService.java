package com.zbkj.service.service.tl.conversation;

import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.conversation.TlMessageReport;
import com.zbkj.common.response.tl.conversation.TlConversationResponse;
import com.zbkj.common.response.tl.conversation.TlMessageResponse;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.request.tl.conversation.MessageSendRequest;
import com.zbkj.common.request.tl.conversation.MessageReportRequest;
import java.util.List;

public interface TlConversationService {
    Long open(Long stallId);
    List<TlConversationResponse> listMine();
    Integer unreadCount();
    PageInfo<TlMessageResponse> messages(Long conversationId, PageParamRequest page);
    Long send(Long conversationId, MessageSendRequest request);
    void markRead(Long conversationId, Long messageId);
    void block(Long conversationId, boolean blocked);
    Boolean isBlockedInConversation(Long conversationId);
    Long report(MessageReportRequest request);
    PageInfo<TlMessageReport> pendingReports(PageParamRequest page);
    void handleReport(Long reportId, boolean resolve, String note);
}
