package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 微信公众号js-sdk响应对象
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
@Schema(name = "WeChatJsSdkConfigResponse对象", description = "微信公众号js-sdk响应对象对象")
public class WeChatJsSdkConfigResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "url")
    private String url;

    @Schema(description = "jsApiTicket")
    private String jsApiTicket;

    @Schema(description = "nonceStr")
    private String nonceStr;

    @Schema(description = "timestamp")
    private Long timestamp;

    @Schema(description = "signature")
    private String signature;

    @Schema(description = "jsApiList")
    private List<String> jsApiList;

    @Schema(description = "debug")
    private Boolean debug;

    @Schema(description = "appid")
    private String appId;
}
