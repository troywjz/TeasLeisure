package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 商品所参与的活动类型
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
public class ProductActivityItemResponse {

    @Schema(description = "参与活动id")
    private Integer id;

    @Schema(description = "活动结束时间")
    private Integer time;

    @Schema(description = "活动参与类型:1=秒杀，2=砍价，3=拼团")
    private String type;
}
