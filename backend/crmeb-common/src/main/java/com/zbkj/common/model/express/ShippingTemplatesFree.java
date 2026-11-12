package com.zbkj.common.model.express;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 免费运费模版
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
@TableName("eb_shipping_templates_free")
@Schema(name = "ShippingTemplatesFree对象", description = "")
public class ShippingTemplatesFree implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "模板ID")
    private Integer tempId;

    @Schema(description = "城市ID")
    private Integer cityId;

    @Schema(description = "描述")
    private String title;

    @Schema(description = "包邮件数")
    private BigDecimal number;

    @Schema(description = "包邮金额")
    private BigDecimal price;

    @Schema(description = "计费方式")
    private Integer type;

    @Schema(description = "分组唯一值")
    private String uniqid;

    @Schema(description = "是否无效")
    private Boolean status;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;


}
