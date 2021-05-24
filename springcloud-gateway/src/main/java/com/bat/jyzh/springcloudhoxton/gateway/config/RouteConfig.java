package com.bat.jyzh.springcloudhoxton.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author ZhengYu
 * @version 1.0 2021/5/24 20:26
 **/
@Configuration
public class RouteConfig {

    @Autowired
    private RouteDefinitionRepository routeDefinitionRepository;
}
