package com.zbkj.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zbkj.common.annotation.StringContains;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 用户表
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
@TableName("eb_user")
@Schema(name = "User对象", description = "用户表")
public class UserSearchRequest extends UserCommonSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "时间")
    private String dateLimit;

    @Schema(description = "用户分组")
    private String groupId;

    @Schema(description = "用户标签")
    private String labelId;

    @Schema(description = "用户登陆类型，h5 = h5， wechat = wechat，routine = routine", allowableValues = "range[h5,wechat,routine]")
    @StringContains(limitValues = {"h5","wechat","routine"}, message = "请选择正确的用户登录类型")
    private String userType;

    @Schema(description = "状态是否正常， 0 = 禁止， 1 = 正常")
    private Boolean status = null;

    @Schema(description = "是否为推广员， 0 = 禁止， 1 = 正常")
    private Boolean isPromoter = null;

    @Schema(description = "消费情况")
    private String payCount;

    @Schema(description = "等级")
    private String level;

    //时间类型
    @Schema(description = "访问情况： 1 = 首次， 2 = 访问过， 3 = 未访问", allowableValues = "range[0,1,2,3]")
    private Integer accessType = 0;

    @Schema(description = "国家，中国CN，其他OTHER")
    private String country;

    @Schema(description = "省份")
    private String province;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "性别，0未知，1男，2女，3保密")
    private String sex;
}
