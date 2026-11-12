package com.zbkj.common.model.bargain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户参与砍价表
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
@TableName("eb_store_bargain_user")
@Schema(name = "StoreBargainUser对象", description = "用户参与砍价表")
public class StoreBargainUser implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "用户参与砍价表ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户ID")
    private Integer uid;

    @Schema(description = "砍价商品id")
    private Integer bargainId;

    @Schema(description = "砍价的最低价")
    private BigDecimal bargainPriceMin;

    @Schema(description = "砍价金额")
    private BigDecimal bargainPrice;

    @Schema(description = "砍掉的价格")
    private BigDecimal price;

    @Schema(description = "状态 1参与中 2 活动结束参与失败 3活动结束参与成功")
    private Integer status;

    @Schema(description = "参与时间")
    private Long addTime;

    @Schema(description = "是否取消")
    private Boolean isDel;


}
