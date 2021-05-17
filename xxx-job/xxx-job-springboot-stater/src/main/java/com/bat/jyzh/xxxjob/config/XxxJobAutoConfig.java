package com.bat.jyzh.xxxjob.config;

import com.bat.jyzh.xxxjob.aspect.JobCronFlushAspect;
import com.bat.jyzh.xxxjob.controller.XxxJobExecutorController;
import com.bat.jyzh.xxxjob.processor.JobRegisterProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * XXX-Job 自动装配类
 *
 * @author ZhengYu
 * @version 1.0 2021/5/16 0:05
 **/
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableConfigurationProperties(XxxJobProperties.class)
public class XxxJobAutoConfig {

    @Bean
    public XxxJobExecutorController xxxJobExecutorController() {
        return new XxxJobExecutorController();
    }

    @Bean
    public JobRegisterProcessor jobRegisterProcessor() {
        return new JobRegisterProcessor();
    }

    @Bean
    public JobCronFlushAspect jobCronFlushAspect() {
        return new JobCronFlushAspect();
    }
}
