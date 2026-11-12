package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户充值 response
 *  +----------------------------------------------------------------------
 *  | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 *  +----------------------------------------------------------------------
 *  | Copyright (c) 2016~2024 https://www.crmeb.com All rights reserved.
 *  +----------------------------------------------------------------------
 *  | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 *  +----------------------------------------------------------------------
 *  | Author: CRMEB Team <admin@crmeb.com>
 *  +----------------------------------------------------------------------
 */
@Data
public class UserRechargeResponse {

    @Schema(description = "充值记录ID")
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

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "昵称")
    private String nickname;
}
