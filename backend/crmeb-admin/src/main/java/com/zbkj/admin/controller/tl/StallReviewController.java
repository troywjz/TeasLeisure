package com.zbkj.admin.controller.tl;

import com.zbkj.common.result.CommonResult;
import com.zbkj.common.model.tl.Stall;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.request.tl.StallSubmissionRequest;
import com.zbkj.common.request.tl.StallVisibilityRequest;
import com.zbkj.common.request.tl.StallDuplicateSearchRequest;
import com.zbkj.common.response.tl.StallOperatorCreateOptionsResponse;
import com.zbkj.common.response.tl.StallDuplicateCandidateResponse;
import com.zbkj.common.annotation.LogControllerAnnotation;
import com.zbkj.common.enums.MethodType;
import com.zbkj.service.service.tl.StallService;
import com.zbkj.service.service.tl.StallCategoryService;
import com.zbkj.service.service.SystemCityService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/tl/stalls/review")
@Tag(name = "茶余摊位审核")
public class StallReviewController {

    @Autowired
    private StallService stallService;
    @Autowired private StallCategoryService stallCategoryService;
    @Autowired private SystemCityService systemCityService;

    @GetMapping
    @PreAuthorize("hasAuthority('tl:stall:review')")
    @Operation(summary = "分页查看待审核地摊投稿")
    public CommonResult<CommonPage<Stall>> pending(@Validated PageParamRequest request) {
        PageInfo<Stall> page = stallService.getPending(request);
        return CommonResult.success(CommonPage.restPage(page));
    }

    @GetMapping("/manage")
    @PreAuthorize("hasAuthority('tl:stall:review')")
    @Operation(summary = "分页筛选已发布与已隐藏摊位")
    public CommonResult<CommonPage<Stall>> manage(@Validated PageParamRequest page,
                                                   @RequestParam(required = false) String keyword,
                                                   @RequestParam(required = false) String cityCode,
                                                   @RequestParam(required = false) String districtCode,
                                                   @RequestParam(required = false) Integer categoryId,
                                                   @RequestParam(required = false) Integer auditStatus) {
        PageInfo<Stall> result = stallService.manage(page, keyword, cityCode, districtCode, categoryId, auditStatus);
        return CommonResult.success(CommonPage.restPage(result));
    }

    @GetMapping("/duplicates")
    @PreAuthorize("hasAuthority('tl:stall:review')")
    @Operation(summary = "查询潜在重复摊位候选")
    public CommonResult<List<StallDuplicateCandidateResponse>> duplicates(@Validated StallDuplicateSearchRequest request) {
        return CommonResult.success(stallService.findDuplicateCandidates(request));
    }

    @GetMapping("/operator/options")
    @PreAuthorize("hasAuthority('tl:stall:review')")
    @Operation(summary = "平台录入摊位表单选项")
    public CommonResult<StallOperatorCreateOptionsResponse> operatorCreateOptions() {
        StallOperatorCreateOptionsResponse response = new StallOperatorCreateOptionsResponse();
        response.setCategories(stallCategoryService.enabled());
        response.setCityTree(systemCityService.getListTree());
        return CommonResult.success(response);
    }

    @PostMapping("/operator")
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.ADD, description = "平台录入摊位投稿")
    @PreAuthorize("hasAuthority('tl:stall:review')")
    @Operation(summary = "平台录入待审核摊位")
    public CommonResult<Long> createByOperator(@Validated @org.springframework.web.bind.annotation.RequestBody StallSubmissionRequest request) {
        return CommonResult.success(stallService.createByOperator(request));
    }

    @PostMapping("/{id}/approve")
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "茶余摊位投稿审核通过")
    @PreAuthorize("hasAuthority('tl:stall:review')")
    @Operation(summary = "通过摊位投稿")
    public CommonResult<String> approve(@PathVariable Long id,
                                        @RequestParam(required = false) String note) {
        stallService.review(id, true, note);
        return CommonResult.success();
    }

    @PostMapping("/{id}/reject")
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "茶余摊位投稿审核驳回")
    @PreAuthorize("hasAuthority('tl:stall:review')")
    @Operation(summary = "驳回摊位投稿")
    public CommonResult<String> reject(@PathVariable Long id, @RequestParam String note) {
        stallService.review(id, false, note);
        return CommonResult.success();
    }

    @PostMapping("/{id}/hide")
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "茶余运营隐藏已发布摊位")
    @PreAuthorize("hasAuthority('tl:stall:review')")
    @Operation(summary = "填写理由并隐藏已发布摊位")
    public CommonResult<String> hide(@PathVariable Long id, @RequestBody @Validated StallVisibilityRequest request) {
        stallService.setManagedVisibility(id, true, request);
        return CommonResult.success();
    }

    @PostMapping("/{id}/restore")
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "茶余运营恢复已隐藏摊位")
    @PreAuthorize("hasAuthority('tl:stall:review')")
    @Operation(summary = "填写理由并恢复已隐藏摊位")
    public CommonResult<String> restore(@PathVariable Long id, @RequestBody @Validated StallVisibilityRequest request) {
        stallService.setManagedVisibility(id, false, request);
        return CommonResult.success();
    }
}
