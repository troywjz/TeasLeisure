package com.zbkj.common.request;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 商品秒杀配置
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
@TableName("StoreSeckillMangerSearchRequest")
@Schema(name = "StoreSeckillMangerSearchRequest", description = "商品秒杀配置")
public class StoreSeckillMangerSearchRequest {

    @Schema(description = "秒杀名称")
    private String name;

    @Schema(description = "状态:0=关闭,1=开启")
    private Boolean status;
}
