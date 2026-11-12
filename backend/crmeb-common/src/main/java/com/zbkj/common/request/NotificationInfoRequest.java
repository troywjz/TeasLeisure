package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 系统通知详情请求对象
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
@Schema(name = "NotificationInfoRequest对象", description = "系统通知详情请求对象")
public class NotificationInfoRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "通知id")
    @NotNull(message = "通知id不能为空")
    private Integer id;

    @Schema(description = "wechat-公众号模板消息，routine-小程序订阅消息，sms-短信")
    @NotEmpty(message = "详情类型不能为空")
    private String detailType;

}
