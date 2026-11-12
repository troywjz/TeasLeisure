package com.zbkj.common.model.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠卷记录表
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
@TableName("eb_store_coupon_user")
@Schema(name = "StoreCouponUser对象", description = "优惠券记录表")
public class StoreCouponUser implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "优惠券发布id")
    private Integer couponId;

    @Schema(description = "兑换的项目id")
    private Integer cid;

    @Schema(description = "领取人id")
    private Integer uid;

    @Schema(description = "优惠券名称")
    private String name;

    @Schema(description = "优惠券的面值")
    private BigDecimal money;

    @Schema(description = "最低消费多少金额可用优惠券")
    private BigDecimal minPrice;

    @Schema(description = "获取方式，send后台发放, 用户领取 get,register用户注册")
    private String type;

    @Schema(description = "状态（0：未使用，1：已使用, 2:已失效）")
    private Integer status;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "开始使用时间")
    private Date startTime;

    @Schema(description = "过期时间")
    private Date endTime;

    @Schema(description = "使用时间")
    private Date useTime;

    @Schema(description = "使用类型 1 全场通用, 2 商品券, 3 品类券")
    private Integer useType;

    @Schema(description = "主键id 商品id/分类id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String primaryKey;

    @Schema(description = "关联订单id")
    private Integer orderId;

}
