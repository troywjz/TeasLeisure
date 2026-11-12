package com.zbkj.front.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zbkj.common.model.user.User;
import com.zbkj.common.result.CommonResult;
import com.zbkj.common.result.CommonResultCode;
import com.zbkj.common.token.FrontTokenComponent;
import com.zbkj.common.utils.RequestUtil;
import com.zbkj.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *  移动端管理端 token验证拦截器 使用前注意需要一个@Bean手动注解，否则注入无效
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
public class FrontTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private FrontTokenComponent frontTokenComponent;

    @Autowired
    private UserService userService;

    //程序处理之前需要处理的业务
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String token = frontTokenComponent.getToken(request);
        if(token == null || token.isEmpty()){
            if (isPublicTlRead(request)) {
                return true;
            }
            //判断路由，部分路由不管用户是否登录都可以访问
            boolean result = frontTokenComponent.checkRouter(RequestUtil.getUri(request));
            if(result){
                return true;
            }
            response.getWriter().write(JSONObject.toJSONString(CommonResult.failed(CommonResultCode.UNAUTHORIZED)));
            return false;
        }

        Boolean result = frontTokenComponent.check(token, request);
        if(!result){
            if (isPublicRead(request)) {
                markAnonymous(request);
                return true;
            }
            response.getWriter().write(JSONObject.toJSONString(CommonResult.failed(CommonResultCode.PERMISSION_EXPIRATION)));
            return false;
        }

        Integer userId = frontTokenComponent.getUserId();
        User user = userId == null ? null : userService.getById(userId);
        if (user == null || !Boolean.TRUE.equals(user.getStatus())) {
            if (isPublicRead(request)) {
                markAnonymous(request);
                return true;
            }
            response.getWriter().write(JSONObject.toJSONString(CommonResult.failed(CommonResultCode.UNAUTHORIZED)));
            return false;
        }
        return true;
    }

    private void markAnonymous(HttpServletRequest request) {
        request.setAttribute(FrontTokenComponent.REQUEST_USER_ANONYMOUS_ATTRIBUTE, true);
    }

    private boolean isPublicRead(HttpServletRequest request) {
        return isPublicTlRead(request) || ("GET".equalsIgnoreCase(request.getMethod())
                && frontTokenComponent.checkRouter(RequestUtil.getUri(request)));
    }

    /** Public discovery reads are anonymous; writes on these paths still require a valid token. */
    private boolean isPublicTlRead(HttpServletRequest request) {
        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            return false;
        }
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        if (contextPath != null && !contextPath.isEmpty() && uri.startsWith(contextPath)) {
            uri = uri.substring(contextPath.length());
        }
        while (uri.endsWith("/")) {
            uri = uri.substring(0, uri.length() - 1);
        }
        if (uri.startsWith("/")) {
            uri = uri.substring(1);
        }
        return uri.matches("api/front/tl/stalls")
                || uri.matches("api/front/tl/stalls/[0-9]+")
                || uri.matches("api/front/tl/categories")
                || uri.matches("api/front/tl/community/posts")
                || uri.matches("api/front/tl/community/posts/[0-9]+")
                || uri.matches("api/front/tl/community/posts/[0-9]+/comments");
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

}
