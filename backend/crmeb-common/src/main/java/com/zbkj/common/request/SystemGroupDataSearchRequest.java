package com.zbkj.common.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "SystemGroupDataSearchRequest对象", description = "组合数据详情表")
public class SystemGroupDataSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;
    @Schema(description = "关键字")
    private String keywords;

    @Schema(description = "分组id")
    private Integer gid;

    @Schema(description = "状态（1：开启；2：关闭；）")
    private Boolean status;

}
