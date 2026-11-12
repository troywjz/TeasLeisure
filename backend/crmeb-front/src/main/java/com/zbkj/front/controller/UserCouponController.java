package com.zbkj.front.controller;

import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.request.UserCouponReceiveRequest;
import com.zbkj.common.response.StoreCouponUserResponse;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.StoreCouponUserService;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 优惠卷控制器
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
@RequestMapping("api/front/coupon")
@Tag(name = "营销 -- 优惠券")
public class UserCouponController {

    @Autowired
    private StoreCouponUserService storeCouponUserService;

    /**
     * 我的优惠券
     */
    @Operation(summary = "我的优惠券")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Parameters({
            @Parameter(name = "type", description = "类型，usable-可用，unusable-不可用", required = true),
            @Parameter(name = "page", description = "页码", required = true),
            @Parameter(name = "limit", description = "每页数量", required = true)
    })
    public CommonResult<CommonPage<StoreCouponUserResponse>> getList(@RequestParam(value = "type") String type,
                                                                     @Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(storeCouponUserService.getMyCouponList(type, pageParamRequest));
    }

    /**
     * 领券
     * @param request UserCouponReceiveRequest 新增参数
     */
    @Operation(summary = "领券")
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public CommonResult<String> receive(@RequestBody @Validated UserCouponReceiveRequest request) {
        if (storeCouponUserService.receiveCoupon(request)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

}



