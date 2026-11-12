package com.zbkj.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 砍价商品查询Request
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
@Schema(name = "StoreBargain对象", description = "砍价表")
public class StoreBargainSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "搜索关键字 商品id或者名称")
    private String keywords;

    @Schema(description = "砍价状态 0(到砍价时间不自动开启)  1(到砍价时间自动开启时间)")
    private Integer status;

}
