package com.bat.jyzh.xxxjob.annotation;

import java.lang.annotation.*;

/**
 * 定时任务参数
 *
 * @author ZhengYu
 * @version 1.0 2021/5/16 1:30
 **/

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JobCronFlush {

    //job 唯一的 key
    String uniqueJobKey();
}
