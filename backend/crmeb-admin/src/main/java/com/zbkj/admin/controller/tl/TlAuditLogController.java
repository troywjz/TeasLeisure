package com.zbkj.admin.controller.tl;

import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.log.SensitiveMethodLog;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.SensitiveMethodLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/tl/audit-logs")
@Tag(name = "茶余操作记录")
@PreAuthorize("hasAuthority('admin:log:sensitive:list')")
public class TlAuditLogController {

    @Autowired
    private SensitiveMethodLogService sensitiveMethodLogService;

    @GetMapping
    public CommonResult<CommonPage<SensitiveMethodLog>> list(@Validated PageParamRequest pageParamRequest) {
        PageInfo<SensitiveMethodLog> page = sensitiveMethodLogService.getTlPageList(pageParamRequest);
        return CommonResult.success(CommonPage.restPage(page));
    }
}
