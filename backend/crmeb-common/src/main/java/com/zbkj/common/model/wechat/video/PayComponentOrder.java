package com.zbkj.common.model.wechat.video;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 组件订单表
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
@TableName("eb_pay_component_order")
@Schema(name = "PayComponentOrder对象", description = "组件订单表")
public class PayComponentOrder implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "订单号,商家自定义订单ID")
    private String orderNo;

    @Schema(description = "微信侧订单id ")
    private Long componentOrderId;

    @Schema(description = "用户的openid")
    private String openid;

    @Schema(description = "商家小程序该订单的页面path，用于微信侧订单中心跳转")
    private String path;

    @Schema(description = "下单时小程序的场景值")
    private Integer scene;

    @Schema(description = "用户id")
    private Integer outUserId;

    @Schema(description = "支付方式，包括：微信支付,货到付款,其他")
    private String payMethod;

    @Schema(description = "支付方式，0，微信支付，1: 货到付款，99: 其他（默认0）")
    private Integer payMethodType;

    @Schema(description = "预支付ID")
    private String prepayId;

    @Schema(description = "预付款时间（拿到prepay_id的时间）")
    private String prepayTime;

    @Schema(description = "该订单最终的金额（单位：分）")
    private Long orderPrice;

    @Schema(description = "运费（单位：分）")
    private Long freight;

    @Schema(description = "优惠金额（单位：分）")
    private Long discountedPrice;

    @Schema(description = "附加金额（单位：分）")
    private Long additionalPrice;

    @Schema(description = "附加金额备注")
    private String additionalRemarks;

    @Schema(description = "发货类型:1: 正常快递, 2: 无需快递, 3: 线下配送, 4: 用户自提 （默认1）")
    private Integer deliveryType;

    @Schema(description = "收件人姓名")
    private String receiverName;

    @Schema(description = "详细收货地址信息")
    private String detailedAddress;

    @Schema(description = "收件人手机号码")
    private String telNumber;

    @Schema(description = "国家")
    private String country;

    @Schema(description = "省份")
    private String province;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "乡镇")
    private String town;

    @Schema(description = "拉起收银台的ticket")
    private String ticket;

    @Schema(description = "ticket有效截止时间")
    private String ticketExpireTime;

    @Schema(description = "订单最终价格（单位：分）")
    private Long finalPrice;

    @Schema(description = "订单状态：10-待付款，11-收银台支付完成（自动流转，对商家来说和10同等对待即可），20-待发货，30-待收货，100-完成，200-全部商品售后之后，订单取消，250-用户主动取消/待付款超时取消/商家取消")
    private Integer status;

    @Schema(description = "是否发货完成")
    private Integer finishAllDelivery;

    @Schema(description = "快递数组")
    private String deliveryList;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "微信支付订单号")
    private String transactionId;

    @Schema(description = "订单支付时间，格式为yyyyMMddHHmmss")
    private String timeEnd;
}
