package com.zbkj.common.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 运费模板请求对象
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
@Schema(name = "ShippingTemplatesRequest对象", description = "运费模板请求对象")
public class ShippingTemplatesRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "模板名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "模板名称必须填写")
    @Length(max = 200, message = "模板名称不能超过200个字符")
    private String name;

    @Schema(description = "计费方式 0（未选择），1(按件数), 2(按重量)，3(按体积)", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "计费方式必须选择")
    @Range(min = 0, max = 3, message = "计费方式选择区间 1(按件数), 2(按重量)，3(按体积)")
    private Integer type;

    @Schema(description = "包邮类型：0-全国包邮，1-部分包邮，2-自定义", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "指定包邮必须选择")
    @Range(min = 0, max = 2, message = "未知的包邮类型")
    private Integer appoint;

    @Schema(description = "排序", example = "0")
    @NotNull(message = "排序数字必须填写")
    @Range(min = 0, max = 9999, message = "排序范围为0~9999")
    private Integer sort;

    @Schema(description = "配送区域及运费，部分包邮、自定义模板时必传")
    private List<ShippingTemplatesRegionRequest> shippingTemplatesRegionRequestList;

    @Schema(description = "包邮区域设置，自定义模板时有值传递")
    private List<ShippingTemplatesFreeRequest> shippingTemplatesFreeRequestList;
}
