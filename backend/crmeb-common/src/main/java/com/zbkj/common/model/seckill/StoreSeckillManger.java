package com.zbkj.common.model.seckill;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品秒杀管理表
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
@TableName("eb_store_seckill_manger")
@Schema(name = "StoreSeckillManger对象", description = "商品秒杀管理表")
public class StoreSeckillManger implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "秒杀名称")
    private String name;

    @Schema(description = "秒杀开始时间段")
    private Integer startTime;

    @Schema(description = "秒杀结束时间段")
    private Integer endTime;

    @Schema(description = "主图")
    private String img;

    @Schema(description = "轮播图")
    private String silderImgs;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态:0=关闭,1=开启")
    private Boolean status;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "0未删除1已删除")
    private Boolean isDel;


}
