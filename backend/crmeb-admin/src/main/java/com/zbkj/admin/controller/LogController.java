package com.zbkj.admin.controller;

import com.zbkj.common.model.log.SensitiveMethodLog;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.SensitiveMethodLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志控制器
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
@Slf4j
@RestController
@RequestMapping("api/admin/log")
@Tag(name = "日志管理")
public class LogController {

    @Autowired
    private SensitiveMethodLogService sensitiveMethodLogService;

    /**
     * 敏感操作日志列表
     */
    @PreAuthorize("hasAuthority('admin:log:sensitive:list')")
    @Operation(summary = "敏感操作日志列表")
    @RequestMapping(value = "/sensitive/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<SensitiveMethodLog>> getList(@Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(sensitiveMethodLogService.getPageList(pageParamRequest)));
    }

}
