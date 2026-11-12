package com.zbkj.common.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 活动维护对象
 * @Auther: 大粽子
 * @Date: 2023/1/5 12:15
 * @Description: 活动controller request
 */
@Data
public class ActivityStyleRequest {

    @Schema(description = "主键")
    private Integer id;

    @Schema(description = "活动名称")
    @NotEmpty(message = "活动名称不能为空")
    private String name;

    @Schema(description = "活动类型0=活动边框，1=活动背景")
    private Boolean type;

    @Schema(description = "开始时间")
    @NotEmpty(message = "开始时间不能为空")
    private String starttime;

    @Schema(description = "结束时间")
    @NotEmpty(message = "结束时间不能为空")
    private String endtime;

    @Schema(description = "活动边框素材地址")
    @NotEmpty(message = "活动素材 不能为空")
    private String style;

    @Schema(description = "状态 0=关闭1=开启")
    private Boolean status;

    @Schema(description = "商品参与类型 0=全部商品参与，1=指定商品参与，2=指定品牌参与，3=指定商品分类参与")
    @NotNull(message = "活动参与类型不能为空")
    @Range(min = 0, max = 4, message = "商品参与类型 0=全部商品参与，1=指定商品参与，2=指定品牌参与，3=指定商品分类参与， 4=指定商户产品")
    private Integer method;

    @Schema(description = "参与活动商品")
    private String products;

}
