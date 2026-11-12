package com.zbkj.admin.config;

import com.zbkj.admin.filter.JwtAuthenticationTokenFilter;
import com.zbkj.admin.filter.NonSuperAdminReadOnlyFilter;
import com.zbkj.admin.manager.AuthenticationEntryPointImpl;
import com.zbkj.admin.manager.CustomAccessDeniedHandler;
import com.zbkj.admin.manager.CustomAuthenticationProvider;
import com.zbkj.common.constants.UploadConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * Security配置
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
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private CorsFilter corsFilter;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Value("${crmeb.non-super-admin-read-only:false}")
    private boolean nonSuperAdminReadOnly;

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public AuthenticationEntryPointImpl unauthorizedHandler() {
        return new AuthenticationEntryPointImpl();
    }

    @Bean
    public CustomAccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(customAuthenticationProvider);
        http.cors(cors -> { });
        http.csrf(AbstractHttpConfigurer::disable);
        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(unauthorizedHandler())
                .accessDeniedHandler(accessDeniedHandler()));
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/admin/login", "/api/admin/validate/code/get").permitAll()
                .requestMatchers("/api/admin/getLoginPic").permitAll()
                .requestMatchers("/api/admin/login/account/detection").permitAll()
                .requestMatchers("/api/admin/validate/code/getcaptchaconfig").permitAll()
                .requestMatchers("/" + UploadConstants.UPLOAD_FILE_KEYWORD + "/**").permitAll()
                .requestMatchers("/" + UploadConstants.DOWNLOAD_FILE_KEYWORD + "/**").permitAll()
                .requestMatchers("/" + UploadConstants.UPLOAD_AFTER_FILE_KEYWORD + "/**").permitAll()
                .requestMatchers("/theme/download/**").permitAll()
                .requestMatchers("/uploads/theme/**").permitAll()
                .requestMatchers("/api/admin/upload/image", "/api/admin/upload/file").authenticated()
                .requestMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
                .requestMatchers("/profile/**").anonymous()
                .requestMatchers("/common/download**").anonymous()
                .requestMatchers("/common/download/resource**").anonymous()
                .requestMatchers("/doc.html", "/swagger-ui.html", "/swagger-ui/**", "/webjars/**").permitAll()
                .requestMatchers("/v3/api-docs/**", "/v3/api-docs.yaml").permitAll()
                .requestMatchers("/druid/**").anonymous()
                .requestMatchers("/captcha/get", "/captcha/check").anonymous()
                .requestMatchers("/api/admin/payment/callback/**").anonymous()
                .requestMatchers("/api/public/**").permitAll()
                .anyRequest().authenticated());
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new NonSuperAdminReadOnlyFilter(nonSuperAdminReadOnly), JwtAuthenticationTokenFilter.class);
        http.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
        http.addFilterBefore(corsFilter, LogoutFilter.class);
        return http.build();
    }
}
