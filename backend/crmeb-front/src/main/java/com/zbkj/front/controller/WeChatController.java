package com.zbkj.front.controller;

import com.zbkj.common.model.wechat.TemplateMessage;
import com.zbkj.common.request.RegisterAppWxRequest;
import com.zbkj.common.request.RegisterThirdUserRequest;
import com.zbkj.common.request.WxBindingPhoneRequest;
import com.zbkj.common.response.LoginResponse;
import com.zbkj.common.response.WeChatJsSdkConfigResponse;
import com.zbkj.common.result.CommonResult;
import com.zbkj.front.service.UserCenterService;
import com.zbkj.service.service.SystemNotificationService;
import com.zbkj.service.service.WechatNewService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 微信缓存表 前端控制器
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
@RestController("WeChatFrontController")
@RequestMapping("api/front/wechat")
@Tag(name = "微信 -- 开放平台")
public class WeChatController {

    @Autowired
    private UserCenterService userCenterService;

    @Autowired
    private WechatNewService wechatNewService;

    @Autowired
    private SystemNotificationService systemNotificationService;

    /**
     * 通过微信code登录
     */
    @Operation(summary = "微信登录公众号授权登录")
    @RequestMapping(value = "/authorize/login", method = RequestMethod.GET)
    @Parameters({
            @Parameter(name = "spread_spid", description = "推荐人id", schema = @Schema(type = "integer")),
            @Parameter(name = "code", description = "code码", required = true, schema = @Schema(type = "string"))
    })
    public CommonResult<LoginResponse> login(@RequestParam(value = "spread_spid", defaultValue = "0", required = false) Integer spreadUid,
                                             @RequestParam(value = "code") String code){
        return CommonResult.success(userCenterService.weChatAuthorizeLogin(code, spreadUid));
    }

    /**
     * 微信登录小程序授权登录
     */
    @Operation(summary = "微信登录小程序授权登录")
    @RequestMapping(value = "/authorize/program/login", method = RequestMethod.POST)
    public CommonResult<LoginResponse> programLogin(@RequestParam String code, @RequestBody @Validated RegisterThirdUserRequest request){
        return CommonResult.success(userCenterService.weChatAuthorizeProgramLogin(code, request));
    }

    /**
     * 微信登录App授权登录
     */
    @Operation(summary = "微信登录App授权登录")
    @RequestMapping(value = "/authorize/app/login", method = RequestMethod.POST)
    public CommonResult<LoginResponse> appLogin(@RequestBody @Validated RegisterAppWxRequest request){
        return CommonResult.success(userCenterService.appLogin(request));
    }

    /**
     * 微信注册绑定手机号
     */
    @Operation(summary = "微信注册绑定手机号")
    @RequestMapping(value = "/register/binding/phone", method = RequestMethod.POST)
    public CommonResult<LoginResponse> registerBindingPhone(@RequestBody @Validated WxBindingPhoneRequest request){
        return CommonResult.success(userCenterService.registerBindingPhone(request));
    }

    /**
     * 获取微信公众号js配置
     */
    @Operation(summary = "获取微信公众号js配置")
    @RequestMapping(value = "/config", method = RequestMethod.GET)
    @Parameter(name = "url", description = "页面地址url")
    public CommonResult<WeChatJsSdkConfigResponse> configJs(@RequestParam(value = "url") String url){
        return CommonResult.success(wechatNewService.getJsSdkConfig(url));
    }

    /**
     * 小程序获取授权logo
     */
    @Operation(summary = "小程序获取授权logo")
    @RequestMapping(value = "/getLogo", method = RequestMethod.GET)
    public CommonResult<Map<String, String>> getLogo(){
        Map<String, String> map = new HashMap<>();
        map.put("logoUrl", userCenterService.getLogo());
        return CommonResult.success(map);
    }

    /**
     * 订阅消息模板列表
     */
    @Operation(summary = "订阅消息模板列表")
    @RequestMapping(value = "/program/my/temp/list", method = RequestMethod.GET)
    @Parameter(name = "type", description = "支付之前：beforePay|支付成功：afterPay|申请退款：refundApply|充值之前：beforeRecharge|创建砍价：createBargain|参与拼团：pink|取消拼团：cancelPink")
    public CommonResult<List<TemplateMessage>> programMyTempList(@RequestParam(name = "type") String type){
        return CommonResult.success(systemNotificationService.getMiniTempList(type));
    }
}



