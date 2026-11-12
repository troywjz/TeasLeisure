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
 * 配置表
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
@TableName("eb_system_config")
@Schema(name = "SystemConfig对象", description = "配置表")
public class SystemConfig implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "配置id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "字段名称")
    private String name;

    @Schema(description = "字段提示文字")
    private String title;

    @Schema(description = "表单id")
    private Integer formId;

    @Schema(description = "值")
    private String value;

    @Schema(description = "是否隐藏")
    private Boolean status;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;
}
