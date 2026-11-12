package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品评价数量和好评度
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
@Schema(name = "StoreProductReplayCountResponse对象", description = "产品评价数量和好评度")
public class StoreProductReplayCountResponse implements Serializable {

    private static final long serialVersionUID=1L;

    public StoreProductReplayCountResponse() {}
    public StoreProductReplayCountResponse(Long sumCount, Long goodCount, Long inCount, Long poorCount, String replyChance, Integer replyStar) {
        this.sumCount = sumCount;
        this.goodCount = goodCount;
        this.inCount = inCount;
        this.poorCount = poorCount;
        this.replyChance = replyChance;
        this.replyStar = replyStar;
    }

    @Schema(description = "评论总数")
    private Long sumCount;

    @Schema(description = "好评总数")
    private Long goodCount;

    @Schema(description = "中评总数")
    private Long inCount;

    @Schema(description = "差评总数")
    private Long poorCount;

    @Schema(description = "好评率")
    private String replyChance;

    @Schema(description = "评分星数")
    private Integer replyStar;


}
