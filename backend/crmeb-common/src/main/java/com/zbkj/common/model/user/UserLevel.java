package com.zbkj.common.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户等级记录表
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
@TableName("eb_user_level")
@Schema(name = "UserLevel对象", description = "用户等级记录表")
public class UserLevel implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户uid")
    private Integer uid;

    @Schema(description = "等级vip")
    private Integer levelId;

    @Schema(description = "会员等级")
    private Integer grade;

    @Schema(description = "0:禁止,1:正常")
    private Boolean status;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "是否已通知")
    private Boolean remind;

    @Schema(description = "是否删除,0=未删除,1=删除")
    private Boolean isDel;

    @Schema(description = "享受折扣")
    private Integer discount;

    @Schema(description = "创建时间")
    private Date updateTime;

    @Schema(description = "创建时间")
    private Date createTime;

}
