package com.zbkj.service.dao.tl.conversation;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbkj.common.model.tl.conversation.TlConversation;
import com.zbkj.common.response.tl.conversation.TlConversationResponse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface TlConversationDao extends BaseMapper<TlConversation> {
    @Select("SELECT c.id AS id, c.stall_id AS stallId, s.name AS stallName, " +
            "u.nickname AS peerName, m.content AS lastMessageContent, c.last_message_time AS lastMessageTime, " +
            "(SELECT COUNT(*) FROM eb_tl_message unread WHERE unread.conversation_id = c.id " +
            "AND unread.is_deleted = 0 AND unread.sender_user_id <> #{userId} " +
            "AND unread.id > CASE WHEN c.visitor_user_id = #{userId} THEN COALESCE(c.visitor_read_message_id, 0) " +
            "ELSE COALESCE(c.owner_read_message_id, 0) END) AS unreadCount, " +
            "CASE WHEN c.is_closed = 0 AND s.audit_status = 1 AND s.is_deleted = 0 " +
            "AND s.owner_user_id = c.owner_user_id AND NOT EXISTS (SELECT 1 FROM eb_tl_user_block b " +
            "WHERE (b.user_id = #{userId} AND b.blocked_user_id = CASE WHEN c.visitor_user_id = #{userId} THEN c.owner_user_id ELSE c.visitor_user_id END) " +
            "OR (b.user_id = CASE WHEN c.visitor_user_id = #{userId} THEN c.owner_user_id ELSE c.visitor_user_id END AND b.blocked_user_id = #{userId})) " +
            "THEN 1 ELSE 0 END AS canSend " +
            "FROM eb_tl_conversation c " +
            "LEFT JOIN eb_tl_stall s ON s.id = c.stall_id " +
            "LEFT JOIN eb_user u ON u.uid = CASE WHEN c.visitor_user_id = #{userId} THEN c.owner_user_id ELSE c.visitor_user_id END " +
            "LEFT JOIN eb_tl_message m ON m.id = c.last_message_id AND m.is_deleted = 0 " +
            "WHERE (c.visitor_user_id = #{userId} OR c.owner_user_id = #{userId}) " +
            "ORDER BY COALESCE(c.last_message_time, c.create_time) DESC, c.id DESC LIMIT 100")
    List<TlConversationResponse> selectMineSummaries(@Param("userId") Integer userId);
}
