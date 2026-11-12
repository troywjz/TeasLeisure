package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * 砍价记录响应对象
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
@Schema(name = "BargainRecordResponse对象", description = "砍价记录响应对象")
public class BargainRecordResponse {

    @Schema(description = "砍价商品ID")
    private Integer id;

    @Schema(description = "关联商品ID")
    private Integer productId;

    @Schema(description = "砍价活动名称")
    private String title;

    @Schema(description = "砍价活动图片")
    private String image;

    @Schema(description = "砍价结束时间")
    private Long stopTime;

    @Schema(description = "用户砍价活动id")
    private Integer bargainUserId;

    @Schema(description = "剩余金额")
    private BigDecimal surplusPrice;

    @Schema(description = "状态 1参与中 2 活动结束参与失败 3活动结束参与成功")
    private Integer status;

    @Schema(description = "是否取消")
    private Boolean isDel;

    @Schema(description = "是否生成订单")
    private Boolean isOrder;

    @Schema(description = "是否支付")
    private Boolean isPay;

    @Schema(description = "订单号")
    private String orderNo;
}
