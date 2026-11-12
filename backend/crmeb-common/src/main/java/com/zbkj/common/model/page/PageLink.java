package com.zbkj.common.model.page;

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
 * <p>
 * 页面链接
 * </p>
 *
 * @author dazongzi
 * @since 2023-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_page_link")
@Schema(name = "PageLink对象", description = "页面链接")
public class PageLink implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "分类id")
    private Integer cateId;

    @Schema(description = "分组1:基础2:分销3:个人中心")
    private Boolean type;

    @Schema(description = "页面名称")
    private String name;

    @Schema(description = "页面链接")
    private String url;

    @Schema(description = "参数")
    private String param;

    @Schema(description = "事例")
    private String example;

    @Schema(description = "状态")
    private Boolean status;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "1是商户的链接")
    private Boolean isMer;


}
