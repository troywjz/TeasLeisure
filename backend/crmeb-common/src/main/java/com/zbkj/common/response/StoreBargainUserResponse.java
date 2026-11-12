package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 用户参与砍价响应体
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
public class StoreBargainUserResponse {

    private static final long serialVersionUID=1L;

    @Schema(description = "用户参与砍价表ID")
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
    private String addTime;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "结束时间")
    private String dataTime;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "当前价")
    private BigDecimal nowPrice;

    @Schema(description = "剩余砍价次数")
    private Integer num;

    @Schema(description = "总砍价次数")
    private Integer peopleNum;

    @Schema(description = "砍价商品")
    private String title;
}
