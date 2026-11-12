package com.zbkj.common.model.wechat;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 微信关键字回复表
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
@TableName("eb_wechat_reply")
@Schema(name = "WechatReply对象", description = "微信关键字回复表")
public class WechatReply implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "微信关键字回复id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "关键字")
    private String keywords;

    @Schema(description = "回复类型")
    private String type;

    @Schema(description = "回复数据")
    private String data;

    @Schema(description = "0=不可用  1 =可用")
    private Boolean status;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;


}
