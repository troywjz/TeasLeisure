package com.zbkj.common.model.system;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 城市表
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
@TableName("eb_system_city")
@Schema(name = "SystemCity对象", description = "城市表")
public class SystemCity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "城市id")
    private Integer cityId;

    @Schema(description = "省市级别")
    private Integer level;

    @Schema(description = "父级id")
    private Integer parentId;

    @Schema(description = "区号")
    private String areaCode;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "合并名称")
    private String mergerName;

    @Schema(description = "经度")
    private String lng;

    @Schema(description = "纬度")
    private String lat;

    @Schema(description = "是否展示")
    private Boolean isShow;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;

}
