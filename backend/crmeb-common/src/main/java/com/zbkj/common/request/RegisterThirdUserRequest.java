package com.zbkj.common.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 用户地址表
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
@Schema(name = "RegisterThirdUserRequest对象", description = "三方用户注册对象")
public class RegisterThirdUserRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "用户昵称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickName;

    @Schema(description = "性别", requiredMode = Schema.RequiredMode.REQUIRED)
    private String sex;

    @Schema(description = "用户个人资料填写的省份")
    private String province;

    @Schema(description = "普通用户个人资料填写的城市")
    private String city;

    @Schema(description = "国家，如中国为CN")
    private String country;

    @Schema(description = "微信小程序用户头像", requiredMode = Schema.RequiredMode.REQUIRED)
    private String avatar;

    @Schema(description = "推广人id")
    @JsonProperty(value = "spread_spid", defaultValue = "0")
    private Integer spreadPid;

    @Schema(description = "微信公众号用户头像", requiredMode = Schema.RequiredMode.REQUIRED)
    private String headimgurl;

    @Schema(description = "用户类型:wechat-公众号，routine-小程序，h5-H5,iosWx-苹果微信，androidWx-安卓微信")
    private String type;

    @Schema(description = "用户openId")
    private String openId;
}
