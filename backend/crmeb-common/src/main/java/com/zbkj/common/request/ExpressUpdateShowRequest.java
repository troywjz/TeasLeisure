package com.zbkj.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 快递公司编辑请求体
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
@TableName("eb_express")
@Schema(name = "ExpressRequest对象", description = "快递公司编辑请求体")
public class ExpressUpdateShowRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "快递公司id")
    @NotNull(message = "快递公司id不能为空")
    private Integer id;

    @Schema(description = "是否显示")
    @NotNull(message = "是否显示不能为空")
    private Boolean isShow;
}
