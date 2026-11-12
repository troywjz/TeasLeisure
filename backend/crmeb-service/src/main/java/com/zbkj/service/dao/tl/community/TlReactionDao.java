package com.zbkj.service.dao.tl.community;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbkj.common.model.tl.community.TlReaction;
import com.zbkj.common.response.tl.community.TlPostLikeCountResponse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface TlReactionDao extends BaseMapper<TlReaction> {
    @Select("<script>SELECT post_id AS postId, COUNT(*) AS likeCount FROM eb_tl_reaction " +
            "WHERE reaction_type = 'like' AND post_id IN " +
            "<foreach collection='postIds' item='id' open='(' separator=',' close=')'>#{id}</foreach> " +
            "GROUP BY post_id</script>")
    List<TlPostLikeCountResponse> countLikesByPostIds(@Param("postIds") List<Long> postIds);
}
