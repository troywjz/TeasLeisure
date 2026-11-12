package com.zbkj.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 数据存储回复消息内容对象
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
@Schema(name = "MessageReplyDataVo对象", description = "数据存储回复消息内容对象")
public class MessageReplyDataVo{

    @Schema(description = "文本消息内容")
    private String Content;

    @Schema(description = "图片/音频链接")
    private String src;

    @Schema(description = "图片/音频媒体ID")
    private String mediaId;

    @Schema(description = "文本消息内容")
    private Integer articleId;
}
