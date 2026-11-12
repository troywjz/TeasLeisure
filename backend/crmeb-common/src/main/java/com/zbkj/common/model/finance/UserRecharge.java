package com.zbkj.common.model.finance;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户充值表
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
@TableName("eb_user_recharge")
@Schema(name = "UserRecharge对象", description = "用户充值表")
public class UserRecharge implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "充值用户UID")
    private Integer uid;

    @Schema(description = "订单号")
    private String orderId;

    @Schema(description = "充值金额")
    private BigDecimal price;

    @Schema(description = "购买赠送金额")
    private BigDecimal givePrice;

    @Schema(description = "充值类型")
    private String rechargeType;

    @Schema(description = "是否充值")
    private Boolean paid;

    @Schema(description = "充值支付时间")
    private Date payTime;

    @Schema(description = "充值时间")
    private Date createTime;

    @Schema(description = "退款金额")
    private BigDecimal refundPrice;

    @Schema(description = "是否上传微信发货管理")
    private Boolean isWechatShipping;

    @Schema(description = "支付服务方订单号")
    private String outTradeNo;
}
