package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 用户更新请求对象
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
@Schema(name = "UserUpdateRequest", description = "用户更新请求对象")
public class UserUpdateRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "uid")
    private Integer uid;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "生日")
    private String birthday;

    //@Schema(description = "身份证号码")
    //private String cardId;

    @Schema(description = "用户备注")
    private String mark;

    @Schema(description = "账号状态调整理由；仅状态变化时必填")
    @Size(max = 500, message = "状态调整理由最多500字")
    private String moderationReason;

    @Schema(description = "状态是否正常， 0 = 禁止， 1 = 正常")
    @NotNull(message = "状态不能为空")
    private Boolean status;

    @Schema(description = "详细地址")
    private String addres;

//    @Schema(description = "等级")
//    private Integer level;

    @Schema(description = "用户分组id")
    private String groupId;

    @Schema(description = "用户标签id")
    private String tagId;

    @Schema(description = "是否为推广员")
    @NotNull(message = "是否为推广员不能为空")
    private Boolean isPromoter;

}
