package com.zbkj.admin.controller.tl;

import com.zbkj.common.model.tl.StallClaim;
import com.zbkj.common.result.CommonResult;
import com.zbkj.common.annotation.LogControllerAnnotation;
import com.zbkj.common.enums.MethodType;
import com.zbkj.service.service.tl.StallClaimService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/admin/tl/stall-claims")
@Tag(name = "茶余摊主认领审核")
public class StallClaimReviewController {
    @Autowired private StallClaimService claimService;

    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('tl:stall:claim:review')")
    @Operation(summary = "待审核认领")
    public CommonResult<List<StallClaim>> pending() { return CommonResult.success(claimService.pending()); }

    @PostMapping("/{id}/approve")
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "茶余摊位认领审核通过")
    @PreAuthorize("hasAuthority('tl:stall:claim:review')")
    public CommonResult<String> approve(@PathVariable Long id, @RequestParam(required = false) String note) {
        claimService.review(id, true, note); return CommonResult.success();
    }

    @PostMapping("/{id}/reject")
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "茶余摊位认领审核驳回")
    @PreAuthorize("hasAuthority('tl:stall:claim:review')")
    public CommonResult<String> reject(@PathVariable Long id, @RequestParam String note) {
        claimService.review(id, false, note); return CommonResult.success();
    }
}
