package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品保障服务响应对象
 *  +----------------------------------------------------------------------
 *  | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 *  +----------------------------------------------------------------------
 *  | Copyright (c) 2016~2025 https://www.crmeb.com All rights reserved.
 *  +----------------------------------------------------------------------
 *  | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 *  +----------------------------------------------------------------------
 *  | Author: CRMEB Team <admin@crmeb.com>
 *  +----------------------------------------------------------------------
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "StoreProductGuaranteeResponse对象", description = "商品保障服务响应对象")
public class StoreProductGuaranteeResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "保障条款Id")
    private Integer id;

    @Schema(description = "保障条款名称")
    private String name;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "条款内容")
    private String content;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "显示状态")
    private Boolean isShow;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "使用商品数")
    private Integer proNum;
}
