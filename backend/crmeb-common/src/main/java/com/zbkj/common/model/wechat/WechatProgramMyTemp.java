package com.zbkj.common.model.wechat;

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
 * 小程序我的模板
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
@TableName("eb_wechat_program_my_temp")
@Schema(name = "WechatProgramMyTemp对象", description = "小程序我的模板")
public class WechatProgramMyTemp implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "微信模板id")
    private Integer tid;

    @Schema(description = "模版标题")
    private String title;

    @Schema(description = "模板关键字id，多个逗号分割")
    private String kid;

    @Schema(description = "场景描述")
    private String sceneDesc;

    @Schema(description = "微信模板id")
    private String tempId;

    @Schema(description = "扩展字段")
    private String extra;

    @Schema(description = "状态 0，禁用，1启用")
    private Boolean status;

    @Schema(description = "应用场景")
    private String type;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;
}
