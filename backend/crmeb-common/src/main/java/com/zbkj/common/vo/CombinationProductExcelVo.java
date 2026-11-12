package com.zbkj.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 拼团商品ExeclVo对象
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
public class CombinationProductExcelVo {

    @Schema(description = "编号")
    private Integer id;

    @Schema(description = "拼团名称")
    private String title;

    @Schema(description = "原价")
    private BigDecimal otPrice;

    @Schema(description = "拼团价")
    private BigDecimal price;

    @Schema(description = "库存剩余")
    private Integer quotaShow;

    @Schema(description = "拼团人数")
    private Integer countPeople;

    @Schema(description = "参与人数")
    private Integer countPeopleAll;

    @Schema(description = "成团数量")
    private Integer countPeoplePink;

    @Schema(description = "销量")
    private Integer sales;

    @Schema(description = "商品状态")
    private String isShow;

    @Schema(description = "拼团结束时间")
    private String stopTime;
}
