package com.zbkj.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 申请短信模板请求对象
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
@Schema(name = "SmsApplyTempRequest对象", description = "申请短信模板请求对象")
public class SmsApplyTempRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "模板标题")
    @NotBlank(message = "模板标题不能为空")
    private String title;

    @Schema(description = "模板内容(仅内容，不需要签名)")
    @NotBlank(message = "模板内容不能为空")
    private String content;

    @Schema(description = "模板类型：1验证码，2通知，3推广")
    @NotNull(message = "模板类型不能为空")
    @Range(min = 1, max = 3, message = "未知的模板类型")
    private Integer type;

}
