package com.zbkj.common.model.theme;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 主题下载记录表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_theme_download")
@Schema(name = "ThemeDownload对象", description = "主题下载记录表")
public class ThemeDownload implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "自增ID")
    private Integer id;

    @Schema(description = "模版名称")
    private String title;

    @Schema(description = "模版ID")
    private Integer tid;

    @Schema(description = "下载时间")
    private Integer downloadTime;

    @Schema(description = "下载地址")
    private String downloadUrl;
}
