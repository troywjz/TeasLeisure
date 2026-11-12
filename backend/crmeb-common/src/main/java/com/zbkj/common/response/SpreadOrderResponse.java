package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import tools.jackson.databind.annotation.JsonSerialize;
import com.zbkj.common.utils.CustomerBigDecimalSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 推广订单响应体
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
@Schema(name = "SpreadOrderResponse对象", description = "推广订单响应体")
public class SpreadOrderResponse {

    @Schema(description = "订单ID")
    private Integer id;

    @Schema(description = "订单号")
    private String orderId;

    @Schema(description = "用户姓名")
    private String realName;

    @Schema(description = "用户电话")
    private String userPhone;

    @JsonSerialize(using = CustomerBigDecimalSerialize.class)
    @Schema(description = "佣金金额")
    private BigDecimal price;

    @Schema(description = "更新时间")
    private Date updateTime;
}
