package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zbkj.common.model.product.StoreProduct;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 商品评论响应体
 * +----------------------------------------------------------------------
 * | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 * +----------------------------------------------------------------------
 * | Copyright (c) 2016~2024 https://www.crmeb.com All rights reserved.
 * +----------------------------------------------------------------------
 * | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 * +----------------------------------------------------------------------
 * | Author: CRMEB Team <admin@crmeb.com>
 * +----------------------------------------------------------------------
 */
@Data
public class StoreProductReplyResponse {
    private static final long serialVersionUID=1L;

    @Schema(description = "评论ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户ID")
    private Integer uid;

    @Schema(description = "订单ID")
    private Integer oid;

    @Schema(description = "商品id")
    private Integer productId;

    @Schema(description = "某种商品类型(普通商品、秒杀商品）")
    private String replyType;

    @Schema(description = "商品分数")
    private Integer productScore;

    @Schema(description = "服务分数")
    private Integer serviceScore;

    @Schema(description = "评论内容")
    private String comment;

    @Schema(description = "评论图片")
    private List<String> pics;

    @Schema(description = "管理员回复内容")
    private String merchantReplyContent;

    @Schema(description = "管理员回复时间")
    private Integer merchantReplyTime;

    @Schema(description = "0未删除1已删除")
    private Boolean isDel;

    @Schema(description = "0未回复1已回复")
    private Boolean isReply;

    @Schema(description = "用户名称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "评论时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "商品规格属性值")
    private String sku;

    private StoreProduct storeProduct;
}
