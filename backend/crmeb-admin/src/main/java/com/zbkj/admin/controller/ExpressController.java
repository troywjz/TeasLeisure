package com.zbkj.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.zbkj.common.model.express.Express;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.ExpressSearchRequest;
import com.zbkj.common.request.ExpressUpdateRequest;
import com.zbkj.common.request.ExpressUpdateShowRequest;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.ExpressService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 快递公司表 前端控制器
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
@RequestMapping("api/admin/express")
@Tag(name = "设置 -- 物流 -- 公司")
public class ExpressController {

    @Autowired
    private ExpressService expressService;

    /**
     * 分页显示快递公司表
     * @param request ExpressSearchRequest 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:express:list')")
    @Operation(summary = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Parameter(name = "keywords", description = "搜索关键字")
    public CommonResult<CommonPage<Express>> getList(@Validated ExpressSearchRequest request,
                                                     @ModelAttribute PageParamRequest pageParamRequest) {
        CommonPage<Express> expressCommonPage = CommonPage.restPage(expressService.getList(request, pageParamRequest));
        return CommonResult.success(expressCommonPage);
    }

    /**
     * 编辑快递公司
     */
    @PreAuthorize("hasAuthority('admin:express:update')")
    @Operation(summary = "编辑")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestBody @Validated ExpressUpdateRequest expressRequest) {
        if (expressService.updateExpress(expressRequest)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     *修改显示状态
     */
    @PreAuthorize("hasAuthority('admin:express:update:show')")
    @Operation(summary = "修改显示状态")
    @RequestMapping(value = "/update/show", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestBody @Validated ExpressUpdateShowRequest expressRequest) {
        if (expressService.updateExpressShow(expressRequest)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 同步物流公司
     */
    @PreAuthorize("hasAuthority('admin:express:sync')")
    @Operation(summary = "同步物流公司")
    @RequestMapping(value = "/sync/express", method = RequestMethod.POST)
    public CommonResult<String> syncExpress() {
        if (expressService.syncExpress()) {
            CommonResult<String> success = CommonResult.success();
            success.setMessage("同步物流公司成功");
            return success;
        }
        return CommonResult.failed("同步物流公司失败");
    }


    /**
     * 查询快递公司表信息
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:express:info')")
    @Operation(summary = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @Parameter(name = "id", description = "快递公司ID", required = true)
    public CommonResult<Express> info(@RequestParam(value = "id") Integer id) {
        return CommonResult.success(expressService.getInfo(id));
   }

    /**
     * 查询全部物流公司
     */
    @PreAuthorize("hasAuthority('admin:express:all')")
    @Operation(summary = "查询全部物流公司")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @Parameter(name = "type", description = "类型：normal-普通，elec-电子面单")
    public CommonResult<List<Express>> all(@RequestParam(value = "type") String type) {
        return CommonResult.success(expressService.findAll(type));
    }

    /**
     * 查询物流公司面单模板
     */
    @PreAuthorize("hasAuthority('admin:express:template')")
    @Operation(summary = "查询物流公司面单模板")
    @RequestMapping(value = "/template", method = RequestMethod.GET)
    @Parameter(name = "com", description = "快递公司编号", required = true)
    public CommonResult<JSONObject> template(@RequestParam(value = "com") String com) {
        return CommonResult.success(expressService.template(com));
    }

//    /**
//     * 获取电子面单模版
//     */
//    @Operation(summary = "查询 电子面单 模版列表")
//    @RequestMapping(value = "/templatedianzi", method = RequestMethod.GET)
//    @Parameter(name = "com", description = "快递公司编号", required = true)
//    public CommonResult<JSONObject> templateDZ(@RequestParam(value = "com") String com) {
//        return CommonResult.success(expressService.templateFor(com));
//    }
}



