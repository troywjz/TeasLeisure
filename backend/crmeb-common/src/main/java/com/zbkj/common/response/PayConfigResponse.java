package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付配置响应对象
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
@Schema(name = "PayConfigResponse对象", description = "支付配置响应对象")
public class PayConfigResponse implements Serializable {

    private static final long serialVersionUID = 7282892323898493847L;

    @Schema(description = "余额支付")
    private Boolean yuePayStatus;

    @Schema(description = "微信支付")
    private Boolean payWechatOpen;

    @Schema(description = "支付宝支付")
    private Boolean aliPayStatus;

    @Schema(description = "用户余额")
    private BigDecimal userBalance;
}
