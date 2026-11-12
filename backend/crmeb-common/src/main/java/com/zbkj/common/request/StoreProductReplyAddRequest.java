package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 评论添加对象
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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "StoreProductReplyAddRequest对象", description = "评论添加对象")
public class StoreProductReplyAddRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "订单编号， 移动端必须传递此参数")
    private String orderNo;

    @Schema(description = "用户id， 后端必须传递此参数")
    private Integer userId;

    @Schema(description = "商品id", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 1, message = "请选择商品")
    private Integer productId;

    @Schema(description = "商品 属性id")
    private String unique;

    @Schema(description = "商品分数", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    @Range(min = 1, max = 5, message = "商品分数为1-5")
    private Integer productScore;

    @Schema(description = "服务分数", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    @Range(min = 1, max = 5, message = "服务分数为1-5")
    private Integer serviceScore;

    @Schema(description = "评论内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请填写评论内容")
    @Length(max = 512, message = "评论内容长度不能超过512个字符")
    private String comment;

    @Schema(description = "评论图片", requiredMode = Schema.RequiredMode.REQUIRED)
    private String pics;

    @Schema(description = "评论人头像 [虚拟评论参数]")
    private String avatar;

    @Schema(description = "评论人昵称 [虚拟评论参数]")
    private String nickname;

    @Schema(description = "商品规格属性值，多规格时用英文逗号拼接")
    private String sku;

    @Schema(description = "评论时间 [虚拟评论参数]，格式: yyyy-MM-dd HH:mm:ss")
    private String createTime;
}
