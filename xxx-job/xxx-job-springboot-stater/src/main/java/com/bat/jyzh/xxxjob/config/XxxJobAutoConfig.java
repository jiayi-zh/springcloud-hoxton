package com.bat.jyzh.xxxjob.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.PostConstruct;

/**
 * XXX-Job 自动装配类
 *
 * @author ZhengYu
 * @version 1.0 2021/5/16 0:05
 **/
@Configuration
@EnableAspectJAutoProxy
@EnableConfigurationProperties(XxxJobProperties.class)
@ComponentScan("com.bat.jyzh.xxxjob")
public class XxxJobAutoConfig {

    @PostConstruct
    public void postBean() {
        System.out.println("自动装载完成");
    }
}
