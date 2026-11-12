package com.zbkj.common.model.product;

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
 * 商品保障服务表
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_product_guarantee")
@Schema(name = "StoreProductGuarantee对象", description = "商品保障服务表")
public class StoreProductGuarantee implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "保障条款名称")
    private String name;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "条款内容")
    private String content;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "显示状态")
    private Boolean isShow;

    @Schema(description = "是否删除")
    private Boolean isDel;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;
}
