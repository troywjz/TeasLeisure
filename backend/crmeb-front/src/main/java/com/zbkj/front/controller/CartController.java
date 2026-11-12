package com.zbkj.front.controller;

import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.CartNumRequest;
import com.zbkj.common.request.CartRequest;
import com.zbkj.common.request.CartResetRequest;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.CartInfoResponse;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.StoreCartService;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 购物车 前端控制器
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
@RequestMapping("api/front/cart")
@Tag(name = "商品 -- 购物车") //配合swagger使用
public class CartController {

    @Autowired
    private StoreCartService storeCartService;

    /**
     * 分页显示购物车表
     */
    @Operation(summary = "分页列表") //配合swagger使用
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Parameters({
            @Parameter(name = "isValid", description = "类型，true-有效商品，false-无效商品", required = true),
            @Parameter(name = "page", description = "页码", required = true),
            @Parameter(name = "limit", description = "每页数量", required = true)
    })
    public CommonResult<CommonPage<CartInfoResponse>> getList(@RequestParam Boolean isValid, @Validated PageParamRequest pageParamRequest) {
        CommonPage<CartInfoResponse> restPage = CommonPage.restPage(storeCartService.getList(pageParamRequest, isValid));
        return CommonResult.success(restPage);
    }

    /**
     * 新增购物车表
     * @param storeCartRequest 新增参数
     */
    @Operation(summary = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<HashMap<String,String>> save(@RequestBody @Validated CartRequest storeCartRequest) {
        String cartId = storeCartService.saveCate(storeCartRequest);
        if (StringUtils.isNotBlank(cartId)) {
            HashMap<String,String> result = new HashMap<>();
            result.put("cartId", cartId);
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 删除购物车表
     * @param ids 购物车ids
     */
    @Operation(summary = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public CommonResult<String> delete(@RequestParam(value = "ids") List<Long> ids) {
        if (storeCartService.deleteCartByIds(ids)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 修改商品数量
     * @param id integer id
     * @param number 修改的产品数量
     */
    @Operation(summary = "修改")
    @RequestMapping(value = "/num", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id, @RequestParam Integer number) {
        if (storeCartService.updateCartNum(id, number)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 获取购物车数量
     */
    @Operation(summary = "获取购物车数量")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public CommonResult<Map<String, Integer>> count(@Validated CartNumRequest request) {
        return CommonResult.success(storeCartService.getUserCount(request));
    }

    /**
     * 购物车重选提交
     * @param resetRequest 重选参数
     * @return 结果
     */
    @Operation(summary = "购物车重选提交")
    @RequestMapping(value = "/resetcart", method = RequestMethod.POST)
    public CommonResult<Object> resetCart(@RequestBody @Validated CartResetRequest resetRequest){
        return CommonResult.success(storeCartService.resetCart(resetRequest));
    }
}



