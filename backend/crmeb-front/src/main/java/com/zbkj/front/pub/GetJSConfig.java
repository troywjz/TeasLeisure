package com.zbkj.front.pub;

import com.zbkj.common.constants.Constants;
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
}
