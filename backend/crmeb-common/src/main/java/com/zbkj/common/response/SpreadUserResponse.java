package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 推广用户响应体
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
public class SpreadUserResponse {

    @Schema(description = "用户id")
    private Integer uid;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "手机号码")
    private String phone;

    @Schema(description = "佣金金额,未提现金额")
    private BigDecimal brokeragePrice;

    @Schema(description = "推广人id")
    private Integer spreadUid;

    @Schema(description = "上级推广员名称")
    private String spreadNickname;

    @Schema(description = "用户购买次数")
    private Integer payCount;

    @Schema(description = "推广用户数")
    private Integer spreadCount;

    @Schema(description = "推广订单数")
    private Integer spreadOrderNum;

    @Schema(description = "推广订单额")
    private BigDecimal spreadOrderTotalPrice;

    @Schema(description = "佣金总金额")
    private BigDecimal totalBrokeragePrice;

    @Schema(description = "推广用户数量")
    private int spreadPeopleCount;

    @Schema(description = "已提现金额")
    private BigDecimal extractCountPrice;

    @Schema(description = "已提现次数")
    private Integer extractCountNum;

    @Schema(description = "冻结佣金")
    private BigDecimal freezeBrokeragePrice;

    @Schema(description = "成为分销员时间")
    private Date promoterTime;
}
