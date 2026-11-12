package com.zbkj.admin.controller;

import com.zbkj.admin.service.AdminLoginService;
import com.zbkj.common.request.AdminAccountDetectionRequest;
import com.zbkj.common.request.LoginAdminUpdatePasswordRequest;
import com.zbkj.common.request.LoginAdminUpdateRequest;
import com.zbkj.common.request.SystemAdminLoginRequest;
import com.zbkj.common.response.MenusResponse;
import com.zbkj.common.response.SystemAdminResponse;
import com.zbkj.common.response.SystemLoginResponse;
import com.zbkj.common.result.CommonResult;
import com.zbkj.common.utils.CrmebUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 管理端登录服务
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
@RequestMapping("api/admin")
@Tag(name = "管理端登录服务")
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @Operation(summary ="PC登录")
    @PostMapping(value = "/login", produces = "application/json")
    public CommonResult<SystemLoginResponse> SystemAdminLogin(@RequestBody @Validated SystemAdminLoginRequest systemAdminLoginRequest, HttpServletRequest request) {
        String ip = CrmebUtil.getClientIp(request);
        SystemLoginResponse systemAdminResponse = adminLoginService.login(systemAdminLoginRequest, ip);
        return CommonResult.success(systemAdminResponse);
    }

    @PreAuthorize("hasAuthority('admin:logout')")
    @Operation(summary ="PC登出")
    @GetMapping(value = "/logout")
    public CommonResult<SystemAdminResponse> SystemAdminLogout() {
        adminLoginService.logout();
        return CommonResult.success();
    }

    @PreAuthorize("hasAuthority('admin:info')")
    @Operation(summary ="获取用户详情")
    @GetMapping(value = "/getAdminInfoByToken")
    public CommonResult<SystemAdminResponse> getAdminInfo() {
        return CommonResult.success(adminLoginService.getInfoByToken());
    }

    /**
     * 获取登录页图片
     * @return Map<String, Object>
     */
    @Operation(summary = "获取登录页图片")
    @RequestMapping(value = "/getLoginPic", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getLoginPic() {
        return CommonResult.success(adminLoginService.getLoginPic());
    }

    /**
     * 获取管理员可访问目录
     */
    @PreAuthorize("hasAuthority('admin:login:menus')")
    @Operation(summary = "获取管理员可访问目录")
    @RequestMapping(value = "/getMenus", method = RequestMethod.GET)
    public CommonResult<List<MenusResponse>> getMenus() {
        return CommonResult.success(adminLoginService.getMenus());
    }


    @Operation(summary ="账号登录检测")
    @RequestMapping(value = "/login/account/detection", method = RequestMethod.POST)
    public CommonResult<Integer> accountDetection(@RequestBody @Validated AdminAccountDetectionRequest request) {
        return CommonResult.success(adminLoginService.accountDetection(request.getAccount()));
    }

    @PreAuthorize("hasAuthority('admin:update:name')")
    @Operation(summary ="修改登录用户信息")
    @RequestMapping(value = "/login/admin/update", method = RequestMethod.POST)
    public CommonResult<SystemLoginResponse> loginAdminUpdate(@RequestBody @Validated LoginAdminUpdateRequest request) {
        if (adminLoginService.loginAdminUpdate(request)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @PreAuthorize("hasAuthority('admin:update:password')")
    @Operation(summary ="修改登录用户密码")
    @RequestMapping(value = "/login/update/password", method = RequestMethod.POST)
    public CommonResult<SystemLoginResponse> loginAdminUpdatePwd(@RequestBody @Validated LoginAdminUpdatePasswordRequest request) {
        if (adminLoginService.loginAdminUpdatePwd(request)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
}
