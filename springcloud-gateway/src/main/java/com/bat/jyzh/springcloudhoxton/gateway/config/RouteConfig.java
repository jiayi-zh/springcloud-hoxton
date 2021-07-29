package com.bat.jyzh.springcloudhoxton.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义路由配置
 *
 * @author ZhengYu
 * @version 1.0 2021/5/24 20:26
 **/
@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, ThrottleGatewayFilterFactory throttle) {

    }
}
