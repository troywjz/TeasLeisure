package com.zbkj.admin.copyright;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 版权信息响应对象
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
@Schema(name = "CopyrightInfoResponse对象", description = "版权信息响应对象")
public class CopyrightInfoResponse {

    @Schema(description = "管理端API域名")
    private String domainUrl;

    @Schema(description = "项目版本号")
    private String version;

    @Schema(description = "版权标签")
    private Integer label;

    @Schema(description = "授权码")
    private String authCode;

    @Schema(description = "版权状态：-2=API域名未配置 -1=未提交，0-待审核，1-授权成功，2-审核失败")
    private Integer status;

    @Schema(description = "公司信息")
    private String companyName;

    @Schema(description = "公司图片")
    private String companyImage;

    @Schema(description = "版权码")
    private String copyright;
}
