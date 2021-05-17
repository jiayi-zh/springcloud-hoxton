package com.bat.jyzh.xxxjob.aspect;

import com.bat.jyzh.xxxjob.annotation.JobCronFlush;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * 注解自动刷新切面
 *
 * @author ZhengYu
 * @version 1.0 2021/5/16 8:24
 **/
@Slf4j
@Aspect
public class JobCronFlushAspect {

    @Pointcut("@annotation(com.bat.jyzh.xxxjob.annotation.JobCronFlush)")
    public void jobCronFlushPointCut() {
    }

    @Around("jobCronFlushPointCut()")
    public Object jobCronFlushPointAround(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String uniqueJobKey = Optional.of(methodSignature)
                .map(MethodSignature::getMethod)
                .map(method -> method.getAnnotation(JobCronFlush.class))
                .map(JobCronFlush::uniqueJobKey)
                .orElse(null);

        Object result = joinPoint.proceed(joinPoint.getArgs());
        if (StringUtils.hasText(uniqueJobKey) && result instanceof String) {
            String cron = (String) result;
            if (StringUtils.hasText(cron)) {
                log.info("任务{}执行时间更新为: {}", uniqueJobKey, cron);
                // TODO 更新
            }
        }
        return result;
    }
}
