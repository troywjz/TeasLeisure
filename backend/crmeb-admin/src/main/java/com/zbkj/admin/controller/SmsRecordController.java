package com.zbkj.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.zbkj.common.request.PageParamRequest;
import com.zbkj.common.request.SmsApplyTempRequest;
import com.zbkj.common.request.SmsModifySignRequest;
import com.zbkj.common.result.CommonResult;
import com.zbkj.common.vo.MyRecord;
import com.zbkj.service.service.SmsService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 短信发送记录表 前端控制器
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
@RequestMapping("api/admin/sms")
@Tag(name = "短信服务")
public class SmsRecordController {

    @Autowired
    private SmsService smsService;

    /**
     * 修改签名
     */
    @PreAuthorize("hasAuthority('admin:sms:modify:sign')")
    @Operation(summary = "修改签名")
    @RequestMapping(value = "/modify/sign", method = RequestMethod.POST)
    public CommonResult<JSONObject> modifySign(@RequestBody @Validated SmsModifySignRequest request) {
        if (smsService.modifySign(request)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 短信模板
     */
    @PreAuthorize("hasAuthority('admin:sms:temps')")
    @Operation(summary = "短信模板")
    @RequestMapping(value = "/temps", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> temps(@ModelAttribute PageParamRequest pageParamRequest) {
        MyRecord myRecord = smsService.temps(pageParamRequest);
        return CommonResult.success(myRecord);
    }

    /**
     * 申请短信模板
     */
    @PreAuthorize("hasAuthority('admin:sms:temp:apply')")
    @Operation(summary = "申请短信模板")
    @RequestMapping(value = "/temp/apply", method = RequestMethod.POST)
    public CommonResult<JSONObject> applyTempMessage(@RequestBody @Validated SmsApplyTempRequest request) {
        if (smsService.applyTempMessage(request)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 模板申请记录
     */
    @PreAuthorize("hasAuthority('admin:sms:applys')")
    @Operation(summary = "模板申请记录")
    @RequestMapping(value = "/applys", method = RequestMethod.POST)
    @Parameter(name = "type", description = "type (1=验证码 2=通知 3=推广)")
    public CommonResult<Map<String, Object>> applys(@RequestParam(name = "type", required = false) Integer type, @ModelAttribute PageParamRequest pageParamRequest) {
        return CommonResult.success(smsService.applys(type, pageParamRequest));
    }

}


