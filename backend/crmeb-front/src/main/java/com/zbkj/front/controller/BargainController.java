package com.zbkj.front.controller;

import com.github.pagehelper.PageInfo;
import com.zbkj.common.page.CommonPage;
import com.zbkj.common.request.BargainFrontRequest;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.response.*;
import com.zbkj.common.result.CommonResult;
import com.zbkj.service.service.StoreBargainService;
import com.zbkj.service.service.StoreBargainUserHelpService;
import com.zbkj.service.service.StoreBargainUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * H5 砍价
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
@RequestMapping("api/front/bargain")
@Tag(name = "砍价商品")
public class BargainController {

    @Autowired
    private StoreBargainService storeBargainService;

    @Autowired
    private StoreBargainUserService storeBargainUserService;

    @Autowired
    private StoreBargainUserHelpService storeBargainUserHelpService;

    /**
     * 砍价首页信息
     */
    @Operation(summary = "砍价首页信息")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public CommonResult<BargainIndexResponse> index(){
        return CommonResult.success(storeBargainService.getIndexInfo());
    }

    /**
     * 砍价商品列表header
     */
    @Operation(summary = "砍价商品列表header")
    @RequestMapping(value = "/header", method = RequestMethod.GET)
    public CommonResult<BargainHeaderResponse> header(){
        return CommonResult.success(storeBargainService.getHeader());
    }

    /**
     * 砍价商品列表
     * @return 砍价商品列表
     */
    @Operation(summary = "砍价商品列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<PageInfo<StoreBargainDetailResponse>> list(@ModelAttribute PageParamRequest pageParamRequest){
        return CommonResult.success(storeBargainService.getH5List(pageParamRequest));
    }

    /**
     * 获取用户砍价信息
     */
    @Operation(summary = "获取用户砍价信息")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public CommonResult<BargainUserInfoResponse> getBargainUserInfo(@ModelAttribute @Validated BargainFrontRequest bargainFrontRequest) {
        return CommonResult.success(storeBargainUserService.getBargainUserInfo(bargainFrontRequest));
    }

    /**
     * 砍价商品详情
     */
    @Operation(summary = "砍价商品详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public CommonResult<BargainDetailH5Response> detail(@PathVariable(value = "id") Integer id) {
        BargainDetailH5Response h5Detail = storeBargainService.getH5Detail(id);
        return CommonResult.success(h5Detail);
    }

    /**
     * 创建砍价活动
     */
    @Operation(summary = "创建砍价活动")
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public CommonResult<Map<String, Object>> start(@RequestBody @Validated BargainFrontRequest bargainFrontRequest) {
        return CommonResult.success(storeBargainService.start(bargainFrontRequest));
    }

    /**
     * 砍价
     */
    @Operation(summary = "砍价")
    @RequestMapping(value = "/help", method = RequestMethod.POST)
    public CommonResult<Map<String, Object>> help(@RequestBody @Validated BargainFrontRequest bargainFrontRequest) {
        return CommonResult.success(storeBargainUserHelpService.help(bargainFrontRequest));
    }

    /**
     * 砍价记录
     */
    @Operation(summary = "砍价记录")
    @RequestMapping(value = "/record", method = RequestMethod.GET)
    public CommonResult<CommonPage<BargainRecordResponse>> recordList(@ModelAttribute PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(storeBargainUserService.getRecordList(pageParamRequest)));
    }

}
