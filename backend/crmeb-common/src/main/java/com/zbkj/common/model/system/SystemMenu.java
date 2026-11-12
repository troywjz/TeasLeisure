package com.zbkj.common.model.system;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统菜单表
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
@TableName("eb_system_menu")
@Schema(name = "SystemMenu对象", description = "系统菜单表")
public class SystemMenu implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "父级ID")
    private Integer pid;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "icon")
    private String icon;

    @Schema(description = "权限标识")
    private String perms;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "类型，M-目录，C-菜单，A-按钮")
    private String menuType;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "显示状态")
    private Boolean isShow;

    @Schema(description = "是否删除")
    @JsonIgnore
    private Boolean isDelte;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    @JsonIgnore
    private Date updateTime;


}
