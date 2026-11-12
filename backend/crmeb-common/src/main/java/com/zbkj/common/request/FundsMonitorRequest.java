package com.zbkj.common.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zbkj.common.annotation.StringContains;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 资金监控
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
@Schema(name = "FundsMonitorRequest对象", description = "资金监控")
public class FundsMonitorRequest extends UserCommonSearchRequest implements Serializable {

    private static final long serialVersionUID = 3362714265772774491L;

    //@Schema(description = "搜索关键字")
    //private String keywords;

    @Schema(description = "添加时间")
    private String dateLimit;

    @Schema(description = "明细类型:recharge-充值支付，admin-后台操作，productRefund商品退款，payProduct购买商品，transferIn佣金转入")
    @StringContains(limitValues = {"recharge", "admin", "productRefund", "payProduct", "transferIn"}, message = "请选择正确的明细类型")
    private String title;

}
