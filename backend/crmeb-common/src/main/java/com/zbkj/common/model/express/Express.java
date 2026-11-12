package com.zbkj.common.model.express;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 快递公司表
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
@TableName("eb_express")
@Schema(name = "Express对象", description = "快递公司表")
public class Express implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "快递公司id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "快递公司简称")
    private String code;

    @Schema(description = "快递公司全称")
    private String name;

    @Schema(description = "是否需要月结账号")
    private Boolean partnerId;

    @Schema(description = "是否需要月结密码")
    private Boolean partnerKey;

    @Schema(description = "是否需要取件网店")
    private Boolean net;

    @Schema(description = "账号")
    private String account;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "网点名称")
    private String netName;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "是否显示")
    private Boolean isShow;

    @Schema(description = "是否可用")
    private Boolean status;
}
