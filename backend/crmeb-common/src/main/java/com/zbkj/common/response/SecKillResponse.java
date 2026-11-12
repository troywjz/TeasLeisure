package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *  秒杀Header时间header响应对象
 *  +----------------------------------------------------------------------
 *  | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 *  +----------------------------------------------------------------------
 *  | Copyright (c) 2016~2024 https://www.crmeb.com All rights reserved.
 *  +----------------------------------------------------------------------
 *  | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 *  +----------------------------------------------------------------------
 *  | Author: CRMEB Team <admin@crmeb.com>
 *  +----------------------------------------------------------------------
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "SecKillResponse对象", description = "秒杀Header时间header响应对象")
public class SecKillResponse {

    public SecKillResponse() {
    }

    public SecKillResponse(Integer id, String slide, String statusName, String time, int status, String timeSwap) {
        this.id = id;
        this.slide = slide;
        this.statusName = statusName;
        this.time = time;
        this.status = status;
        this.timeSwap = timeSwap;
    }

    @Schema(description = "秒杀时段id")
    private Integer id;

    @Schema(description = "秒杀时段轮播图")
    private String slide;

    @Schema(description = "秒杀时段状态名称")
    private String statusName; // 已结束 抢购中 即将开始

    @Schema(description = "秒杀时段状态")
    private int status;

    @Schema(description = "秒杀时段时间信息")
    private String time;

    @Schema(description = "秒杀时段结束时间")
    private String timeSwap;

    @Schema(description = "是否选中")
    private Boolean isCheck = false;
}
