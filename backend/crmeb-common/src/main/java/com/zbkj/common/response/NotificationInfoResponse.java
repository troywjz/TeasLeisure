package com.zbkj.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统通知详情相应对象
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
@Schema(name = "NotificationInfoResponse对象", description = "系统通知详情相应对象")
public class NotificationInfoResponse implements Serializable {

    private static final long serialVersionUID = -3214167698001861141L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "模板id(公用)")
    private String tempId;

    @Schema(description = "模板说明(短信)")
    private String title;

    @Schema(description = "模板编号(公用)")
    private String tempKey;

    @Schema(description = "内容(公用)")
    private String content;

    @Schema(description = "模板名")
    private String name;

    @Schema(description = "状态,1-开启，2-关闭")
    private Integer status;
}
