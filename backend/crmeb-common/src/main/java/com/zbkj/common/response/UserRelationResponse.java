package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户收藏响应对象
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
@Schema(name = "UserRelationResponse对象", description = "用户收藏响应对象")
public class UserRelationResponse implements Serializable {

    private static final long serialVersionUID = -7274202699019791930L;

    @Schema(description = "收藏id")
    private Integer id;

    @Schema(description = "商品ID")
    private Integer productId;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "商品名称")
    private String storeName;

    @Schema(description = "商品图片")
    private String image;

    @Schema(description = "商品价格")
    private BigDecimal price;
}
