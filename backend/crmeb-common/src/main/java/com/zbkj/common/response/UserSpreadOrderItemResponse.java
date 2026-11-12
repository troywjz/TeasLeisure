package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 推广订单信息
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
@Schema(name = "UserSpreadOrderItemResponse对象", description = "推广订单信息")
public class UserSpreadOrderItemResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "推广条数")
    private Integer count = 0;

    @Schema(description = "推广年月")
    private String time;

    @Schema(description = "推广订单信息")
    private List<UserSpreadOrderItemChildResponse> child = new ArrayList<>();
}
