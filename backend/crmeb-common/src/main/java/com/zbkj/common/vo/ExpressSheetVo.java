package com.zbkj.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统电子面单对象
 *  +----------------------------------------------------------------------
 *  | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 *  +----------------------------------------------------------------------
 *  | Copyright (c) 2016~2024 https://www.crmeb.com All rights reserved.
 *  +----------------------------------------------------------------------
 *  | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 *  +----------------------------------------------------------------------
 *  | Author: CRMEB Team <admin@crmeb.com>
 *  +----------------------------------------------------------------------
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "ExpressSheetVo对象", description = "系统电子面单对象")
public class ExpressSheetVo {

    public ExpressSheetVo() {
    }

    public ExpressSheetVo(Integer exportId, String exportCom, String exportTempId, String exportToName, String exportToTel, String exportToAddress, String exportSiid, Integer exportOpen) {
        this.exportId = exportId;
        this.exportCom = exportCom;
        this.exportTempId = exportTempId;
        this.exportToName = exportToName;
        this.exportToTel = exportToTel;
        this.exportToAddress = exportToAddress;
        this.exportSiid = exportSiid;
        this.exportOpen = exportOpen;
    }

    @Schema(description = "快递公司简称，物流、电子面单开通必填")
    private Integer exportId;

    @Schema(description = "快递公司简称，物流、电子面单开通必填")
    private String exportCom;

    @Schema(description = "快递公司模板Id、电子面单开通必填")
    private String exportTempId;

    @Schema(description = "快递面单发货人姓名，物流、电子面单开通必填")
    private String exportToName;

    @Schema(description = "快递面单发货人电话，物流、电子面单开通必填")
    private String exportToTel;

    @Schema(description = "发货人详细地址，物流、电子面单开通必填")
    private String exportToAddress;

    @Schema(description = "电子面单打印机编号，物流、电子面单开通必填")
    private String exportSiid;

    @Schema(description = "电子面单是否开启")
    private Integer exportOpen;

}
