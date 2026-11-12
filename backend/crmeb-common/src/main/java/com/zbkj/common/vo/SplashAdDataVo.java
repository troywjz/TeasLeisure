package com.zbkj.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "SplashAdDataVo", description = "开屏广告数据VO对象")
public class SplashAdDataVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "链接地址")
    private String linkUrl;

    @Schema(description = "图片地址")
    private String imageUrl;

    @Schema(description = "排序")
    private Integer sort;

}
