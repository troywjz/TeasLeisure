package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 拼团列表响应体
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
public class StorePinkAdminListResponse {

    private static final long serialVersionUID=1L;

    @Schema(description = "拼团ID")
    private Integer id;

    @Schema(description = "用户id")
    private Integer uid;

    @Schema(description = "拼团总人数")
    private Integer people;

    @Schema(description = "开始时间")
    private String addTime;

    @Schema(description = "结束时间")
    private String stopTime;

    @Schema(description = "团长id 0为团长")
    private Integer kId;

    @Schema(description = "状态1进行中2已完成3未完成")
    private Integer status;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "几人参团")
    private Integer countPeople;

    @Schema(description = "拼团商品")
    private String title;
}
