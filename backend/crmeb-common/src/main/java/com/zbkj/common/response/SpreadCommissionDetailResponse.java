package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.zbkj.common.model.user.UserBrokerageRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 推广佣金明细响应对象
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
@Schema(name = "SpreadCommissionDetailResponse对象", description = "推广佣金明细响应对象")
public class SpreadCommissionDetailResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    public SpreadCommissionDetailResponse() {}

    public SpreadCommissionDetailResponse(String date, List<UserBrokerageRecord> list) {
        this.date = date;
        this.list = list;
    }

    @Schema(description = "月份")
    private String date;

    @Schema(description = "数据")
    private List<UserBrokerageRecord> list;


}
