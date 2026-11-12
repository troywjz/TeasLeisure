package com.zbkj.common.request;

import com.zbkj.common.annotation.StringContains;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 用户公共搜索请求对象
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "UserCommonSearchRequest", description = "用户公共搜索请求对象")
public class UserCommonSearchRequest extends PageParamRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "搜索类型，all-全部，uid-UID,nickname-用户昵称,phone-手机号")
    @StringContains(limitValues = {"all","uid","nickname","phone"}, message = "未知的用户搜索类型")
    private String searchType;

    @Schema(description = "搜索内容")
    private String content;
}
