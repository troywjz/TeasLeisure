package com.zbkj.front.controller.tl;
import com.zbkj.common.request.tl.TlContentReportRequest;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.tl.TlContentReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("api/front/tl/content-reports") @Tag(name ="茶余内容举报")
public class TlContentReportController {
 @Autowired private TlContentReportService service;
 @PostMapping @Operation(summary = "举报已发布摊位/动态/评论")
 public CommonResult<Long> submit(@RequestBody @Validated TlContentReportRequest request){return CommonResult.success(service.submit(request));}
}
