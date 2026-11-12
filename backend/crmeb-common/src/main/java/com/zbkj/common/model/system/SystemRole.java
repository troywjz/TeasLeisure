package com.zbkj.common.model.system;

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
 * 身份管理表
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
@TableName("eb_system_role")
@Schema(name = "SystemRole对象", description = "身份管理表")
public class SystemRole implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "身份管理id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "身份管理名称")
    private String roleName;

    @Schema(description = "身份管理权限(menus_id)")
    private String rules;

    private Integer level;

    @Schema(description = "状态：0-关闭，1-正常")
    private Boolean status;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;

}
