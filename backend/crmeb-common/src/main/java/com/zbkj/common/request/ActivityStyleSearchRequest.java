package com.zbkj.common.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;
/**
 * @Auther: 大粽子
 * @Date: 2023/1/5 12:08
 * @Description: 活动样式搜索对象
 */
@Data
public class ActivityStyleSearchRequest {
    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "活动名称不能为空")
    private String name;

    @Schema(description = "活动类型0=活动边框，1=活动背景")
    private Boolean type;

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date starttime;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endtime;

    @Schema(description = "状态 0=关闭1=开启")
    private Boolean status;

    @Schema(description = "状态 -1=已结束，0=未开始，1=进行中")
    private Integer runningStatus;

    @Schema(description = "商品参与类型 0=全部商品参与，1=指定商品参与，2=指定品牌参与，3=指定商品分类参与， 4=指定商户产品")
    @Range(min = 0, max = 4, message = "商品参与类型 0=全部商品参与，1=指定商品参与，2=指定品牌参与，3=指定商品分类参与， 4=指定商户产品")
    private Integer method;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private Date createtime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    private Date updatetime;

}
