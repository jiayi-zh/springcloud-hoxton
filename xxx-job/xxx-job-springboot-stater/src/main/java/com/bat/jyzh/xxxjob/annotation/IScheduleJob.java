package com.bat.jyzh.xxxjob.annotation;

import com.bat.jyzh.xxxjob.enums.ExecutorRouteStrategyEnum;

import java.lang.annotation.*;

/**
 * 定时任务参数
 *
 * @author ZhengYu
 * @version 1.0 2021/5/16 1:30
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IScheduleJob {

    //job 唯一的 key
    String uniqueJobKey();

    // 任务描述
    String description();

    // 执行策略
    ExecutorRouteStrategyEnum executorRouteStrategy = ExecutorRouteStrategyEnum.RANDOM;

    // Cron
    String cron() default "";

    // 任务超时时间，单位秒
    int timeout() default -1;

    // 失败重试次数
    int retryCount() default -1;

    // 负责人 -> 当前定时任务的开发人员
    String responsiblePerson();

    // 报警邮件
    String alarmEmail() default "";

    // 任务参数
    String param() default "";
}
