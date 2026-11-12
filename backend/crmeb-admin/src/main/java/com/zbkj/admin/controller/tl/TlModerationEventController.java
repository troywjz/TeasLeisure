package com.zbkj.admin.controller.tl;

import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.TlModerationEvent;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.tl.TlModerationEventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/tl/moderation-events")
@Tag(name = "茶余审核状态历史")
@PreAuthorize("hasAuthority('admin:log:sensitive:list')")
public class TlModerationEventController {
    @Autowired
    private TlModerationEventService service;

    @GetMapping
    public CommonResult<CommonPage<TlModerationEvent>> list(@Validated PageParamRequest page,
            @RequestParam(required = false) String targetType, @RequestParam(required = false) Long targetId) {
        PageInfo<TlModerationEvent> result = service.list(page, targetType, targetId);
        return CommonResult.success(CommonPage.restPage(result));
    }
}
