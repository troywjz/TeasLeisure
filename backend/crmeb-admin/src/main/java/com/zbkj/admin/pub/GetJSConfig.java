package com.zbkj.admin.pub;

import com.zbkj.common.constants.Constants;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.SystemConfigService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: crmeb
 * @author: 大粽子
 * @create: 2021-09-23 09:18
 **/
@Slf4j
@RestController
@RequestMapping("api/public/jsconfig")
@Tag(name = "公共JS配置")
public class GetJSConfig {

    @Autowired
    private SystemConfigService systemConfigService;

    @PreAuthorize("hasAuthority('public:jsconfig:getcrmebchatconfig')")
    @Operation(summary = "CRMEB-chat客服统计")
    @RequestMapping(value = "/getcrmebchatconfig", method = RequestMethod.GET)
    public String set(){
        return systemConfigService.getValueByKey(Constants.JS_CONFIG_CRMEB_CHAT_TONGJI);
    }

    @Operation(summary = "获取移动端域名")
    @RequestMapping(value = "/get/front/domain", method = RequestMethod.GET)
    public CommonResult<String> getFrontDomain() {
        return CommonResult.success(systemConfigService.getFrontDomain());
    }

    @Operation(summary = "获取平台当前的素材地址")
    @RequestMapping(value = "/get/admin/mediadomain", method = RequestMethod.GET)
    public CommonResult<String> getMediaDomain() {
        return CommonResult.success(systemConfigService.getMediaDomain());
    }
}
