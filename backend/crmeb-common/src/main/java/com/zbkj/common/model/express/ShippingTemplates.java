package com.zbkj.common.model.express;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 *  运费模版对象
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
@TableName("eb_shipping_templates")
@Schema(name = "ShippingTemplates对象", description = "")
public class ShippingTemplates implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "模板名称")
    private String name;

    @Schema(description = "计费方式 0（未选择），1(按件数), 2(按重量)，3(按体积)")
    private Integer type;

    @Schema(description = "包邮类型：0-全国包邮，1-部分包邮，2-自定义")
    private Integer appoint;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;


}
