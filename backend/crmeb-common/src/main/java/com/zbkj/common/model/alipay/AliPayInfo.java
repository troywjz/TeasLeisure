package com.zbkj.common.model.alipay;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 支付宝订单表
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
@TableName("eb_ali_pay_info")
@Schema(name = "AliPayInfo对象", description = "支付宝订单表")
public class AliPayInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "支付宝分配给开发者的应用ID")
    private String appId;

    @Schema(description = "发送请求的时间，格式'yyyy-MM-dd HH:mm:ss'")
    private String timestamp;

    @Schema(description = "订单描述")
    private String body;

    @Schema(description = "订单标题")
    private String subject;

    @Schema(description = "商户订单号")
    private String outTradeNo;

    @Schema(description = "订单相对超时时间")
    private String timeoutExpress;

    @Schema(description = "订单绝对超时时间")
    private String timeExpire;

    @Schema(description = "订单总金额。单位为元，精确到小数点后两位")
    private String totalAmount;

    @Schema(description = "公用回传参数,本参数必须进行UrlEncode之后才可以发送给支付宝")
    private String passbackParams;

    @Schema(description = "网关返回码")
    private String code;

    @Schema(description = "网关返回码描述")
    private String msg;

    @Schema(description = "业务返回码")
    private String subCode;

    @Schema(description = "业务返回码描述")
    private String subMsg;

    @Schema(description = "签名")
    private String sign;

    @Schema(description = "该交易在支付宝系统中的交易流水号")
    private String tradeNo;

    @Schema(description = "收款支付宝账号对应的支付宝唯一用户号")
    private String sellerId;

    @Schema(description = "支付宝服务器主动通知商户服务器里指定的页面http/https路径")
    private String notifyUrl;


}
