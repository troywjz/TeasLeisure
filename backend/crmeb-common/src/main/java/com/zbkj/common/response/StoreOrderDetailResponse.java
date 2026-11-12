package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.zbkj.common.vo.StoreOrderInfoOldVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单信息响应对象（pc列表用）
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
@Schema(name = "StoreOrderDetailResponse对象", description = "订单信息响应对象（pc列表用）")
public class StoreOrderDetailResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "订单ID")
    private Integer id;

    @Schema(description = "订单号")
    private String orderId;

    @Schema(description = "用户id")
    private Integer uid;

    @Schema(description = "实际支付金额")
    private BigDecimal payPrice;

    @Schema(description = "订单商品总数")
    private Integer totalNum;

    @Schema(description = "支付方式")
    private String payType;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "订单状态（0：待发货；1：待收货；2：已收货，待评价；3：已完成；）")
    private Integer status;

    @Schema(description = "商品信息")
    private List<StoreOrderInfoOldVo> productList = new ArrayList<>();

    @Schema(description = "订单状态")
    private Map<String, String> statusStr;

    @Schema(description = "支付方式")
    private String payTypeStr;

    @Schema(description = "是否删除")
    private Boolean isDel;

    @Schema(description = "退款图片")
    private String refundReasonWapImg;

    @Schema(description = "退款用户说明")
    private String refundReasonWapExplain;

    @Schema(description = "退款时间")
    private Date refundReasonTime;

    @Schema(description = "前台退款原因")
    private String refundReasonWap;

    @Schema(description = "不退款的理由")
    private String refundReason;

    @Schema(description = "退款金额")
    private BigDecimal refundPrice;

    @Schema(description = "0 未退款 1 申请中 2 已退款")
    private Integer refundStatus;

    @Schema(description = "核销码")
    private String verifyCode;

    @Schema(description = "配送方式 1=快递 ，2=门店自提")
    private Integer shippingType;

    @Schema(description = "发货类型 express 发货，send 送货，fictitious虚拟")
    private String deliveryType;

    @Schema(description = "快递名称/送货人姓名")
    private String deliveryName;

    @Schema(description = "快递单号/手机号")
    private String deliveryId;

    @Schema(description = "快递公司简称")
    private String deliveryCode;

    @Schema(description = "拼团id 0没有拼团")
    private Integer pinkId;

    @Schema(description = "订单类型")
    private String orderType;

    @Schema(description = "订单管理员备注")
    private String remark;

    @Schema(description = "用户姓名")
    private String realName;

    @Schema(description = "商品总价")
    private BigDecimal proTotalPrice;

    @Schema(description = "优惠券金额")
    private BigDecimal couponPrice;

    @Schema(description = "改价前支付金额")
    private BigDecimal beforePayPrice;

    @Schema(description = "支付状态")
    private Boolean paid;

    @Schema(description = "订单类型:0-普通订单，1-视频号订单")
    private Integer type;

    @Schema(description = "是否改价,0-否，1-是")
    private Boolean isAlterPrice;

    @Schema(description = "商家寄件单号图片")
    private String shipmentPic;

    @Schema(description = "商家寄件订单任务id")
    private String shipmentTaskId;

    @Schema(description = "商家寄件订单单号")
    private String shipmentOrderId;

    @Schema(description = "商品总价")
    private BigDecimal totalPrice;

    @Schema(description = "原始邮费")
    private BigDecimal totalPostage;

    @Schema(description = "实际支付邮费")
    private BigDecimal payPostage;
}
