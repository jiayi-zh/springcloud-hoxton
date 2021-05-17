package com.bat.jyzh.xxxjob.examples.job;

import com.bat.jyzh.xxxjob.annotation.IScheduleJob;
import com.bat.jyzh.xxxjob.annotation.JobCronFlush;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * {@link IScheduleJob} 定义定时任务的基本元信息
 * {@link JobCronFlush} 可以修改定时任务的执行时间
 *
 * @author ZhengYu
 * @version 1.0 2021/5/16 1:15
 **/
@Slf4j
@Component
@IScheduleJob(uniqueJobKey = "test_task_01", description = "测试任务", cron = "0/5 * * * * ? *", responsiblePerson = "郑宇")
public class TestStaticTask extends IJobHandler implements ApplicationRunner {

    @Autowired
    private TestStaticTask testStaticTask;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        return null;
    }

    @JobCronFlush(uniqueJobKey = "test_task_01")
    public String flushJobCron() {
        return "0/10 * * * * ? *";
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                // 测试 ... 刷新定时任务时间
//                testStaticTask.flushJobCron();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
