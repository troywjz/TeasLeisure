package com.zbkj.admin.pub;

import com.alibaba.fastjson.JSONObject;
import com.zbkj.common.result.CommonResult;
import com.zbkj.common.vo.QrCodeVo;
import com.zbkj.service.service.QrCodeService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类的详细说明
 *
 * @author Han
 * @version 1.0.0
 * @Date 2024/11/25
 */
@Slf4j
@RestController
@RequestMapping("api/public/wechat/mini")
@Tag(name = "微信小程序公共控制器")
public class WechatMiniCommonController {

    @Autowired
    private QrCodeService qrCodeService;

    @Operation(summary = "获取微信二维码")
    @RequestMapping(value = "/get/qrcode", method = RequestMethod.POST)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "生成小程序码 path 和 scene 不能为空 可查看具体参数https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/qrcode-link/qr-code/getUnlimitedQRCode.html#%E8%B0%83%E7%94%A8%E6%96%B9%E5%BC%8F", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = Object.class)))
    public CommonResult<QrCodeVo> getWecahtQrCode(@RequestBody JSONObject data) {
        return CommonResult.success(qrCodeService.getWecahtQrCode(data));
    }

}
