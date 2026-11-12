package com.zbkj.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 微信小程序订阅消息Vo对象(从微信获取的)
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
@Schema(name = "RoutineTemplateKeyVo对象", description = "微信小程序订阅消息KeyVo对象(从微信获取的)")
public class RoutineTemplateKeyVo {

    @Schema(description = "关键词 id，选用模板时需要")
    private Integer kid;

    @Schema(description = "关键词内容")
    private String name;

    @Schema(description = "关键词内容对应的示例")
    private String example;

    @Schema(description = "参数类型")
    private String rule;

}
