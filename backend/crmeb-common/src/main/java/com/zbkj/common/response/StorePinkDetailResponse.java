package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 拼团表
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
@Schema(name = "StorePinkDetailResponse对象", description = "拼团详情响应对象")
public class StorePinkDetailResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "拼团ID")
    private Integer id;

    @Schema(description = "用户id")
    private Integer uid;

    @Schema(description = "订单id 生成")
    private String orderId;

    @Schema(description = "购买总金额")
    private BigDecimal totalPrice;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "订单状态（0：待发货；1：待收货；2：已收货，待评价；3：已完成；）")
    private Integer orderStatus;

    @Schema(description = "0 未退款 1 申请中 2 已退款 3退款中")
    private Integer refundStatus;

}
