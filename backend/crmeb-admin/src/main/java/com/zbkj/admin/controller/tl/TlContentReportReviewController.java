package com.zbkj.admin.controller.tl;
import com.github.pagehelper.PageInfo;
import com.zbkj.common.model.tl.TlContentReport;
import com.zbkj.common.annotation.LogControllerAnnotation;
import com.zbkj.common.enums.MethodType;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.tl.TlContentReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("api/admin/tl/content-reports") @Tag(name ="茶余内容举报处理")
@PreAuthorize("hasAuthority('tl:content:report')")
public class TlContentReportReviewController {
 @Autowired private TlContentReportService service;
 @GetMapping public CommonResult<CommonPage<TlContentReport>> pending(@Validated PageParamRequest page){PageInfo<TlContentReport> result=service.pending(page);return CommonResult.success(CommonPage.restPage(result));}
 @PostMapping("/{id}/resolve") @LogControllerAnnotation(intoDB=true,methodType=MethodType.UPDATE,description="茶余内容举报处理") public CommonResult<String> resolve(@PathVariable Long id,@RequestParam(defaultValue="false") boolean hideTarget,@RequestParam(required=false) String note){service.handle(id,true,hideTarget,note);return CommonResult.success();}
 @PostMapping("/{id}/dismiss") @LogControllerAnnotation(intoDB=true,methodType=MethodType.UPDATE,description="茶余内容举报驳回") public CommonResult<String> dismiss(@PathVariable Long id,@RequestParam String note){service.handle(id,false,false,note);return CommonResult.success();}
}
