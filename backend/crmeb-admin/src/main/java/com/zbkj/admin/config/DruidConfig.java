package com.zbkj.admin.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.jakarta.StatViewServlet;
import com.alibaba.druid.support.jakarta.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * Druid配置组件
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
public class DruidConfig {

    @Bean
    @ConditionalOnProperty(prefix = "crmeb.druid.monitor", name = "enabled", havingValue = "true")
    public ServletRegistrationBean druidServlet(Environment environment) { // 主要实现WEB监控的配置处理
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*"); // 进行druid监控的配置处理操作
        servletRegistrationBean.addInitParameter("allow", environment.getProperty("crmeb.druid.monitor.allow", "127.0.0.1,::1"));
        servletRegistrationBean.addInitParameter("loginUsername", requiredMonitorCredential(environment, "username"));
        servletRegistrationBean.addInitParameter("loginPassword", requiredMonitorCredential(environment, "password"));
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    @ConditionalOnProperty(prefix = "crmeb.druid.monitor", name = "enabled", havingValue = "true")
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());

        filterRegistrationBean.addUrlPatterns("/*"); // 所有请求进行监控处理
        //不必监控的请求
        filterRegistrationBean.addInitParameter("exclusions", "*.html,*.png,*.ico,*.js,*.gif,*.jpg,*.css,/druid/*");
        return filterRegistrationBean;
    }

    private String requiredMonitorCredential(Environment environment, String name) {
        String value = environment.getProperty("crmeb.druid.monitor." + name);
        if (!StringUtils.hasText(value)) {
            throw new IllegalStateException("crmeb.druid.monitor." + name + " is required when Druid monitoring is enabled");
        }
        return value;
    }

    @Bean("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }
}

