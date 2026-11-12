package com.zbkj.common.model.system;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 组合数据详情表
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
@TableName("eb_system_group_data")
@Schema(name = "SystemGroupData对象", description = "组合数据详情表")
public class SystemGroupData implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "组合数据详情ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "对应的数据组id")
    private Integer gid;

    @Schema(description = "数据组对应的数据值（json数据）")
    private String value;

    @Schema(description = "数据排序")
    private Integer sort;

    @Schema(description = "状态（1：开启；0：关闭；）")
    private Boolean status;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;


}
