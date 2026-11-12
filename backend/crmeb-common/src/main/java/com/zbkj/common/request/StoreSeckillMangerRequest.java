package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 秒杀商品管理Request对象
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
@Schema(name = "StoreSeckillMangerRequest对象", description = "")
public class StoreSeckillMangerRequest {

    @Schema(description = "秒杀配置id")
    private Integer id;

    @Schema(description = "秒杀时段名称")
    @NotBlank(message = "秒杀时段名称不能为空")
    @Length(max = 255, message = "秒杀时段名称不能超过255个字符")
    private String name;

//    @Schema(description = "秒杀开始时间段")
//    private Integer startTime;
//
//    @Schema(description = "秒杀结束时间段")
//    private Integer endTime;

    @Schema(description = "秒杀结束时间段")
    @NotBlank(message = "秒杀时间段不能为空")
    private String time; // 接收参数一个字段，入库时分割为startTime/endTime

    @Schema(description = "主图")
    private String img;

    @Schema(description = "轮播图")
    @NotBlank(message = "轮播图不能为空")
    private String silderImgs;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态:0=关闭,1=开启")
    @NotNull(message = "状态不能为空")
    private Boolean status;

    @Schema(description = "0未删除1已删除")
    private Boolean isDel;
}
