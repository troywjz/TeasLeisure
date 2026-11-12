package com.zbkj.front.controller.tl;

import com.zbkj.common.model.tl.StallCategory;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.tl.StallCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/front/tl/categories")
public class StallCategoryController {
    @Autowired private StallCategoryService service;
    @GetMapping public CommonResult<List<StallCategory>> list(){return CommonResult.success(service.enabled());}
}
