package com.zbkj.front.controller.admin;

import com.zbkj.common.response.MobileAdminManageStatisticsResponse;
import com.zbkj.common.result.CommonResult;
import com.zbkj.front.service.MobileAdminManageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 移动端商家工作台
 */
@RestController
@RequestMapping("api/front/manage")
@Tag(name = "移动端商家管理-工作台")
public class MobileAdminManageController {

    @Autowired
    private MobileAdminManageService mobileAdminManageService;

    @Operation(summary = "商家工作台统计")
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public CommonResult<MobileAdminManageStatisticsResponse> statistics() {
        return CommonResult.success(mobileAdminManageService.getStatistics());
    }
}
