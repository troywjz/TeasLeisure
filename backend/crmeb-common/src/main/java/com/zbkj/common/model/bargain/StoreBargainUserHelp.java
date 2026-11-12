package com.zbkj.common.model.bargain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 砍价用户帮助表
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
@TableName("eb_store_bargain_user_help")
@Schema(name = "StoreBargainUserHelp对象", description = "砍价用户帮助表")
public class StoreBargainUserHelp implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "砍价用户帮助表ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "帮助的用户id")
    private Integer uid;

    @Schema(description = "砍价商品ID")
    private Integer bargainId;

    @Schema(description = "用户参与砍价表id")
    private Integer bargainUserId;

    @Schema(description = "帮助砍价多少金额")
    private BigDecimal price;

    @Schema(description = "添加时间")
    private Long addTime;

    @Schema(description = "用户昵称")
    @TableField(exist = false)
    private String nickname;

    @Schema(description = "用户头像")
    @TableField(exist = false)
    private String avatar;

    @Schema(description = "添加时间(前端用)")
    @TableField(exist = false)
    private String addTimeStr;
}
