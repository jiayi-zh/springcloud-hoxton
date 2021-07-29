package com.bat.jyzh.springcloudhoxton.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Spring Cloud Gateway 源码详解
 *
 * @author ZhengYu
 * @version 1.0 2021/5/24 17:11
 **/
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayApplication.class, args);
    }
}
