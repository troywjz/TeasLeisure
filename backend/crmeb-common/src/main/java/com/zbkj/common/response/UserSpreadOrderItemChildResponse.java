package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 推广订单信息子集
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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "UserSpreadOrderItemChildResponse对象", description = "推广订单信息子集")
public class UserSpreadOrderItemChildResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "订单号")
    private String orderId;

    @Schema(description = "返佣时间")
    private Date time;

    @Schema(description = "返佣金额")
    private BigDecimal number;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "订单显示类型")
    private String type;
}
