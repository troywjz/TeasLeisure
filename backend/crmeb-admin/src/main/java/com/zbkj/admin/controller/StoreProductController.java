package com.zbkj.admin.controller;

import com.zbkj.common.annotation.CustomResponseAnnotation;
import com.zbkj.common.annotation.LogControllerAnnotation;
import com.zbkj.common.enums.MethodType;
import com.zbkj.common.model.product.StoreProduct;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.*;
import com.zbkj.common.response.CopyProductResponse;
import com.zbkj.common.response.StoreProductInfoResponse;
import com.zbkj.common.response.StoreProductResponse;
import com.zbkj.common.response.StoreProductTabsHeader;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.StoreCartService;
import com.zbkj.service.service.StoreProductService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 商品表 前端控制器
 * +----------------------------------------------------------------------
 * | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 * +----------------------------------------------------------------------
 * | Copyright (c) 2016~2024 https://www.crmeb.com All rights reserved.
 * +----------------------------------------------------------------------
 * | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 * +----------------------------------------------------------------------
 * | Author: CRMEB Team <admin@crmeb.com>
 * +----------------------------------------------------------------------
 */
@Slf4j
@RestController
@RequestMapping("api/admin/store/product")
@Tag(name = "商品") //配合swagger使用
public class StoreProductController {

    @Autowired
    private StoreProductService storeProductService;

    @Autowired
    private StoreCartService storeCartService;

    /**
     * 分页显示商品表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:product:list')")
    @Operation(summary = "分页列表") //配合swagger使用
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreProductResponse>> getList(@Validated StoreProductSearchRequest request,
                                                                  @Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(storeProductService.getAdminList(request, pageParamRequest)));
    }

    @PreAuthorize("hasAuthority('admin:product:listbyids')")
    @Operation(summary = "根据id集合获取商品列表") //配合swagger使用
    @RequestMapping(value = "/listids/{ids}", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreProduct>> getListByIds(@PathVariable(value = "ids") List<Integer> ids) {
        return CommonResult.success(CommonPage.restPage(storeProductService.getListInIds(ids)));
    }

    /**
     * 新增商品
     * @param request 新增参数
     */
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.ADD, description = "新增商品")
    @PreAuthorize("hasAuthority('admin:product:save')")
    @Operation(summary = "新增商品")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated StoreProductAddRequest request) {
        if (storeProductService.save(request)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 删除商品表
     * @param id Integer
     */
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.DELETE, description = "删除商品")
    @PreAuthorize("hasAuthority('admin:product:delete')")
    @Operation(summary = "删除")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestBody @PathVariable Integer id, @RequestParam(value = "type", required = false, defaultValue = "recycle")String type) {
        if (storeProductService.deleteProduct(id, type)) {
            if (type.equals("recycle")) {
                storeCartService.productStatusNotEnable(id);
            } else {
                storeCartService.productDelete(id);
            }
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 恢复已删除商品表
     * @param id Integer
     */
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "恢复回收站商品")
    @PreAuthorize("hasAuthority('admin:product:restore')")
    @Operation(summary = "恢复商品")
    @RequestMapping(value = "/restore/{id}", method = RequestMethod.GET)
    public CommonResult<String> restore(@RequestBody @PathVariable Integer id) {
        if (storeProductService.reStoreProduct(id)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 商品修改
     * @param storeProductRequest 商品参数
     */
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "修改商品")
    @PreAuthorize("hasAuthority('admin:product:update')")
    @Operation(summary = "商品修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestBody @Validated StoreProductAddRequest storeProductRequest) {
        if (storeProductService.update(storeProductRequest)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 商品详情
     * @param id 商品id
     */
    @PreAuthorize("hasAuthority('admin:product:info')")
    @Operation(summary = "商品详情")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public CommonResult<StoreProductInfoResponse> info(@PathVariable Integer id) {
        return CommonResult.success(storeProductService.getInfo(id));
   }

    /**
     * 商品tabs表头数据
     */
    @PreAuthorize("hasAuthority('admin:product:tabs:headers')")
   @Operation(summary = "商品表头数量")
   @RequestMapping(value = "/tabs/headers", method = RequestMethod.GET)
   public CommonResult<List<StoreProductTabsHeader>> getTabsHeader(@Validated StoreProductHeaderRequest request) {
        return CommonResult.success(storeProductService.getTabsHeader(request));
   }


    /**
     * 上架
     */
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "上架商品")
    @PreAuthorize("hasAuthority('admin:product:up')")
    @Operation(summary = "上架")
    @RequestMapping(value = "/putOnShell/{id}", method = RequestMethod.GET)
    public CommonResult<String> putOn(@PathVariable Integer id) {
        if (storeProductService.putOnShelf(id)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 下架
     */
    @LogControllerAnnotation(intoDB = true, methodType = MethodType.UPDATE, description = "下架商品")
    @PreAuthorize("hasAuthority('admin:product:down')")
    @Operation(summary = "下架")
    @RequestMapping(value = "/offShell/{id}", method = RequestMethod.GET)
    public CommonResult<String> offShell(@PathVariable Integer id) {
        if (storeProductService.offShelf(id)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    @PreAuthorize("hasAuthority('admin:product:import:product')")
    @Operation(summary = "导入99Api商品")
    @RequestMapping(value = "/importProduct", method = RequestMethod.POST)
    @Parameters({
            @Parameter(name = "form", description = "导入平台1=淘宝，2=京东，3=苏宁，4=拼多多, 5=天猫", required = true, schema = @Schema(type = "integer")),
            @Parameter(name = "url", description = "URL", required = true, schema = @Schema(type = "string")),
    })
    public CommonResult<StoreProductRequest> importProduct(
            @RequestParam @Valid int form,
            @RequestParam @Valid String url) throws IOException, JSONException {
        StoreProductRequest productRequest = storeProductService.importProductFromUrl(url, form);
        return CommonResult.success(productRequest);
    }

    /**
     * 获取复制商品配置
     */
    @PreAuthorize("hasAuthority('admin:product:copy:config')")
    @Operation(summary = "获取复制商品配置")
    @RequestMapping(value = "/copy/config", method = RequestMethod.POST)
    public CommonResult<Map<String, Object>> copyConfig() {
        return CommonResult.success(storeProductService.copyConfig());
    }

    @PreAuthorize("hasAuthority('admin:product:copy:product')")
    @Operation(summary = "复制平台商品")
    @RequestMapping(value = "/copy/product", method = RequestMethod.POST)
    public CommonResult<CopyProductResponse> copyProduct(@RequestBody @Valid StoreCopyProductRequest request) {
        return CommonResult.success(storeProductService.copyProduct(request.getUrl()));
    }

    @PreAuthorize("hasAuthority('admin:product:quick:stock:add')")
    @Operation(summary = "快捷添加库存")
    @RequestMapping(value = "/quick/stock/add", method = RequestMethod.POST)
    public CommonResult<String> quickAddStock(@RequestBody @Validated ProductAddStockRequest request) {
        if (storeProductService.quickAddStock(request)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

}



