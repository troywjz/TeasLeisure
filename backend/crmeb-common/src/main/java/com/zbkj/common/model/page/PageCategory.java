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
 * 页面链接分类
 * </p>
 *
 * @author dazongzi
 * @since 2023-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_page_category")
@Schema(name = "PageCategory对象", description = "页面链接分类")
public class PageCategory implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "父类id")
    private Integer pid;

    @Schema(description = "类型:link、special、product、product_category、custom")
    private String type;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态")
    private Boolean status;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "层级 个位数")
    private Boolean level;

    @Schema(description = "是否商户 0平台，商户id")
    private Integer isMer;


}
