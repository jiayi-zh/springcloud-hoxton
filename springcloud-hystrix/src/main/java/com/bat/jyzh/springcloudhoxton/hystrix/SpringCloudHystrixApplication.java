package com.bat.jyzh.springcloudhoxton.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * Spring Cloud Hystrix 源码学习
 *
 * @author ZhengYu
 * @version 1.0 2021/4/25 13:58
 **/
@EnableHystrix
@SpringBootApplication
public class SpringCloudHystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudHystrixApplication.class, args);
    }

}
