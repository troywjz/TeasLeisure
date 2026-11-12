package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 核销
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
@Schema(name = "SystemWriteOffOrderSearchRequest对象", description = "核销订单搜索")
public class SystemWriteOffOrderSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "核销点ID")
    private Integer storeId;

    @Schema(description = "时间")
    private String dateLimit;

    @Schema(description = "关键字")
    private String keywords;

}
