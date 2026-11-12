package com.zbkj.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 商品评论查询对象
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
@Schema(name = "StoreProductReplySearchRequest对象", description = "商品评论查询对象")
public class StoreProductReplySearchRequest extends UserCommonSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "商品名称")
    private String productSearch;

    @Schema(description = "0未回复1已回复")
    private Boolean isReply;

    @Schema(description = "时间区间")
    private String dateLimit;
}
