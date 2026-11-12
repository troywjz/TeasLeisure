package com.zbkj.common.request;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "SystemCitySearchRequest对象", description = "城市表列表搜索条件")
public class SystemCitySearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "父级id", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "父级id不能为空")  //不可为空
    @DecimalMin(value = "0", message = "父级id必须大于等于0") //数字最小值为0
    private Integer parentId;

}
