package com.bat.jyzh.xxxjob.processor;

import com.alibaba.fastjson.JSONObject;
import com.bat.jyzh.xxxjob.annotation.IScheduleJob;
import com.bat.jyzh.xxxjob.config.XxxJobProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 将自定义Job注册到调度中心
 *
 * @author ZhengYu
 * @version 1.0 2021/5/16 9:43
 **/
@Slf4j
@Component
public class JobRegisterProcessor implements BeanPostProcessor {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private XxxJobProperties xxxJobProperties;

    Map<String, Object> scheduleTaskCacheMap = new ConcurrentHashMap<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Optional.of(bean)
                .map(Object::getClass)
                .map(clazz -> clazz.getAnnotation(IScheduleJob.class))
                .ifPresent(iScheduleJob -> {
                    log.info("{} 注册任务需检查并注册到调度中心 {}", JSONObject.toJSONString(iScheduleJob), JSONObject.toJSONString(bean));
                    scheduleTaskCacheMap.put(iScheduleJob.uniqueJobKey(), bean);
                });
        return null;
    }

    @EventListener
    public void onApplicationStartEvent(ApplicationStartedEvent applicationStartedEvent) {
        System.out.println("开始向调度中心注册");
    }
}
