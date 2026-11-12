package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 推广人信息
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
@Schema(name = "UserSpreadPeopleItemResponse对象", description = "推广人信息")
public class UserSpreadPeopleItemResponse implements Serializable {

    private static final long serialVersionUID=1L;


    @Schema(description = "用户编号")
    private Integer uid;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "添加时间")
    private String time;

    @Schema(description = "推广人数")
    private Integer childCount;

    @Schema(description = "订单数量")
    private Integer orderCount;

    @Schema(description = "订单金额")
    private BigDecimal numberCount;

}
