package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;

/**
 * 拼团商品列表header响应对象
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
@Schema(name = "CombinationHeaderResponse对象", description = "拼团商品列表header响应对象")
public class CombinationHeaderResponse {

    @Schema(description = "拼团7位用户头像")
    private List<String> avatarList;

    @Schema(description = "拼团总人数")
    private Integer totalPeople;

    @Schema(description = "拼团banner数组")
    private List<HashMap<String, Object>> bannerList;
}
