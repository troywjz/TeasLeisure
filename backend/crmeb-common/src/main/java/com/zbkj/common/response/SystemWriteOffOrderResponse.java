package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.io.Serializable;

import com.zbkj.common.page.CommonPage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订单表
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
@Schema(name = "SystemWriteOffOrderResponse对象", description = "核销订单")
public class SystemWriteOffOrderResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "订单总数量")
    private Long total = 0L;

    @Schema(description = "订单总金额")
    private BigDecimal orderTotalPrice;

    @Schema(description = "退款总金额")
    private BigDecimal refundTotalPrice;

    @Schema(description = "退款总单数")
    private Integer refundTotal = 0;

    @Schema(description = "订单列表")
    private CommonPage<StoreOrderItemResponse> list;
}
