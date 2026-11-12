package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.zbkj.common.vo.StoreOrderInfoOldVo;
import com.zbkj.common.model.system.SystemStore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单详情响应对象
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
@Schema(name = "StoreOrderInfoResponse对象", description = "订单详情响应对象")
public class StoreOrderInfoResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "订单ID")
    private Integer id;

    @Schema(description = "订单号")
    private String orderId;

    @Schema(description = "用户id")
    private Integer uid;

    @Schema(description = "用户姓名")
    private String realName;

    @Schema(description = "用户电话")
    private String userPhone;

    @Schema(description = "详细地址")
    private String userAddress;

    @Schema(description = "订单商品总数")
    private Integer totalNum;

    @Schema(description = "订单总价")
    private BigDecimal totalPrice;

    @Schema(description = "实际支付金额")
    private BigDecimal payPrice;

    @Schema(description = "支付邮费")
    private BigDecimal payPostage;

    @Schema(description = "优惠券金额")
    private BigDecimal couponPrice;

    @Schema(description = "抵扣金额")
    private BigDecimal deductionPrice;

    @Schema(description = "支付方式")
    private String payType;

    @Schema(description = "支付状态")
    private Boolean paid;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "订单状态（-1 : 申请退款 -2 : 退货成功 0：待发货；1：待收货；2：已收货；3：待评价；-1：已退款）")
    private Integer status;

    @Schema(description = "0 未退款 1 申请中 2 已退款")
    private Integer refundStatus;

    @Schema(description = "发货记录类型，1正常、2电子面单,3商家发货 发货类型必传 =3 时根据描述填写必填字段，注意为了方便本身不做必填校验")
    private String expressRecordType;

    @Schema(description = "快递名称/送货人姓名")
    private String deliveryName;

    @Schema(description = "发货类型 express 发货，send 送货，fictitious虚拟")
    private String deliveryType;

    @Schema(description = "快递单号/手机号")
    private String deliveryId;

    @Schema(description = "快递公司简称")
    private String deliveryCode;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "是否删除")
    private Boolean isDel;

    @Schema(description = "管理员备注")
    private String remark;

    @Schema(description = "退款金额")
    private BigDecimal refundPrice;

    @Schema(description = "使用积分")
    private Integer useIntegral;

    @Schema(description = "给用户退了多少积分")
    private Integer backIntegral;

    @Schema(description = "核销码")
    private String verifyCode;

    @Schema(description = "配送方式 1=快递 ，2=门店自提")
    private Integer shippingType;

    @Schema(description = "订单状态")
    private Map<String, String> statusStr;

    @Schema(description = "支付方式")
    private String payTypeStr;

    @Schema(description = "用户昵称")
    private String nikeName;

    @Schema(description = "用户电话")
    private String phone;

    @Schema(description = "订单商品详情")
    List<StoreOrderInfoOldVo> orderInfo;

    @Schema(description = "提货点")
    private SystemStore systemStore;

    @Schema(description = "推广人名称")
    private String spreadName;

    @Schema(description = "商品总价")
    private BigDecimal proTotalPrice;

    @Schema(description = "退款时间")
    private Date refundReasonTime;

    @Schema(description = "退款图片")
    private String refundReasonWapImg;

    @Schema(description = "商家寄件单号图片")
    private String shipmentPic;

    @Schema(description = "商家寄件订单任务id")
    private String shipmentTaskId;

    @Schema(description = "商家寄件订单单号")
    private String shipmentOrderId;

    @Schema(description = "商家寄件快递单号")
    private String shipmentNum;

    @Schema(description = "订单类型")
    private String orderTypeText;
}
