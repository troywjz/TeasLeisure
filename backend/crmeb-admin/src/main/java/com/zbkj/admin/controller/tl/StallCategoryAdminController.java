package com.zbkj.admin.controller.tl;

import com.zbkj.common.model.tl.StallCategory;
import com.zbkj.common.annotation.LogControllerAnnotation;
import com.zbkj.common.enums.MethodType;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.tl.StallCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/admin/tl/categories")
@PreAuthorize("hasAuthority('tl:category:manage')")
public class StallCategoryAdminController {
    @Autowired private StallCategoryService service;
    @GetMapping public CommonResult<List<StallCategory>> list(){return CommonResult.success(service.all());}
    @PostMapping
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "茶余摊位分类新增或修改")
    public CommonResult<Integer> save(@RequestBody StallCategory item){return CommonResult.success(service.save(item));}
    @DeleteMapping("/{id}")
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "茶余摊位分类停用")
    public CommonResult<String> delete(@PathVariable Integer id){service.delete(id);return CommonResult.success();}
}
