package com.zbkj.front.controller.tl;

import com.github.pagehelper.PageInfo;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.tl.StallSearchRequest;
import com.zbkj.common.request.tl.StallTodayStatusRequest;
import com.zbkj.common.response.tl.StallPublicResponse;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.tl.StallService;
import com.zbkj.service.service.tl.StallDailyStatusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/front/tl/stalls")
@Tag(name = "茶余地摊发现")
public class StallFrontController {

    @Autowired
    private StallService stallService;

    @Autowired
    private StallDailyStatusService dailyStatusService;

    @GetMapping
    @Operation(summary = "分页浏览已审核地摊")
    public CommonResult<CommonPage<StallPublicResponse>> discover(@Validated StallSearchRequest request) {
        PageInfo<StallPublicResponse> page = stallService.discover(request);
        return CommonResult.success(CommonPage.restPage(page));
    }

    @GetMapping("/{id}")
    @Operation(summary = "查看已审核地摊详情")
    public CommonResult<StallPublicResponse> detail(@PathVariable Long id) {
        StallPublicResponse stall = stallService.getPublished(id);
        if (stall == null) {
            return CommonResult.failed("地摊信息不存在或暂不可见");
        }
        return CommonResult.success(stall);
    }

    @PutMapping("/{id}/today-status")
    @Operation(summary = "摊主更新今天的出摊状态")
    public CommonResult<String> updateTodayStatus(@PathVariable Long id,
                                                   @RequestBody @Validated StallTodayStatusRequest request) {
        dailyStatusService.updateToday(id, request);
        return CommonResult.success();
    }

}
