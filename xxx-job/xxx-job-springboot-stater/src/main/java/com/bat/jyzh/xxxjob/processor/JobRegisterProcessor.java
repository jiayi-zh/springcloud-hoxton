package com.bat.jyzh.xxxjob.processor;

import com.bat.jyzh.xxxjob.annotation.IScheduleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationUtils;

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
public class JobRegisterProcessor implements BeanPostProcessor {

    @Value("${spring.application.name}")
    private String applicationName;

    Map<String, Object> scheduleTaskCacheMap = new ConcurrentHashMap<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Optional.of(bean)
                .map(obj -> AnnotationUtils.findAnnotation(obj.getClass(), IScheduleJob.class))
                .ifPresent(iScheduleJob -> scheduleTaskCacheMap.put(iScheduleJob.uniqueJobKey(), bean));
        return bean;
    }

    @EventListener
    public void handleApplicationStartEvent(ApplicationStartedEvent applicationStartedEvent) {
        log.info("开始向调度中心注册定时任务信息, 执行器个数: 1, 定时任务个数: {}", scheduleTaskCacheMap.size());

        log.info("检测并创建执行器 -> {}", applicationName);

        scheduleTaskCacheMap.forEach((k, v) -> log.info("检测、创建、开启 定时任务 -> {}", k));
    }
}
