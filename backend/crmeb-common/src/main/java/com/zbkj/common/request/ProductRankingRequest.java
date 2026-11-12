package com.zbkj.common.request;

import com.zbkj.common.constants.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 商品排行请求对象
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
@Schema(name = "ProductRankingRequest对象", description = "商品排行请求对象")
public class ProductRankingRequest implements Serializable {

    private static final long serialVersionUID = 3362714265772774491L;

    @Schema(description = "排序参数:pageviews-浏览量,collectNum-收藏数,addCartNum-加购数,salesNum-销量,salesAmount-销售额")
    @NotBlank(message = "请选择排序参数")
    private String sortKey;

    @Schema(description = "时间参数")
    @NotBlank(message = "请先选择时间")
    private String dateLimit;

    @Schema(description = "页码", example = Constants.DEFAULT_PAGE + "")
    private int page = Constants.DEFAULT_PAGE;

    @Schema(description = "每页数量", example = Constants.DEFAULT_LIMIT + "")
    private int limit = Constants.DEFAULT_LIMIT;
}
