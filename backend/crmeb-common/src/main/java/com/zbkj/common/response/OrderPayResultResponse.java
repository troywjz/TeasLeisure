package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.zbkj.common.vo.AliPayJsResultVo;
import com.zbkj.common.vo.WxPayJsResultVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订单支付结果 Response
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
@Schema(name = "OrderPayResultResponse对象", description = "订单支付结果响应对象")
public class OrderPayResultResponse {

    private static final long serialVersionUID=1L;

    @Schema(description = "支付状态")
    private Boolean status;

    @Schema(description = "微信调起支付参数对象")
    private WxPayJsResultVo jsConfig;

    @Schema(description = "支付类型")
    private String payType;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "订单支付宝支付")
    private String alipayRequest;

    @Schema(description = "支付宝调起支付参数对象（app支付专用）")
    private AliPayJsResultVo aliPayConfig;
}
