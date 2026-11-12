package com.zbkj.common.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 组合数据表
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
@TableName("eb_system_group")
@Schema(name = "SystemGroupRequest对象", description = "组合数据表")
public class SystemGroupRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "数据组名称")
    @Length(max = 50, message = "数据组名称长度不能超过50个字符")
    private String name;

    @Schema(description = "简介")
    @Length(max = 256, message = "数据组名称长度不能超过256个字符")
    private String info;

    @Schema(description = "form 表单 id")
    private Integer formId;

}
