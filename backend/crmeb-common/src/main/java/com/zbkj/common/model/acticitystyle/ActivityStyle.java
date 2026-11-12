package com.zbkj.common.model.acticitystyle;

import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 活动气氛样式，包含活动边框和活动背景
 * </p>
 *
 * @author dazongzi
 * @since 2023-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_activity_style")
@Schema(name = "ActivityStyle对象", description = "")
public class ActivityStyle implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "活动名称")
    private String name;

    @Schema(description = "活动类型0=活动边框，1=活动背景")
    private Boolean type;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "开始时间")
    private Date starttime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "结束时间")
    private Date endtime;

    @Schema(description = "活动边框素材地址")
    private String style;

    @Schema(description = "状态 0=关闭1=开启")
    private Boolean status;

    @Schema(description = "商品参与类型 0=全部商品参与，1=指定商品参与，2=指定品牌参与，3=指定商品分类参与，4=指定商户产品")
    private Integer method;

    @Schema(description = "参与活动商品")
    private String products;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private Date createtime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    private Date updatetime;


}
