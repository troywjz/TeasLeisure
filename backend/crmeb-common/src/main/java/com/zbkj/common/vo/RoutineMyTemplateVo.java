package com.zbkj.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 微信小程序订阅消息Vo对象
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
@Schema(name = "RoutineMyTemplateVo对象", description = "微信小程序订阅消息Vo对象")
public class RoutineMyTemplateVo {

    @Schema(description = "模板ID")
    private String priTmplId;

    @Schema(description = "模板标题")
    private String title;

    @Schema(description = "模板内容")
    private String content;

    @Schema(description = "模板示例")
    private String example;

    @Schema(description = "类型")
    private String type;
}
