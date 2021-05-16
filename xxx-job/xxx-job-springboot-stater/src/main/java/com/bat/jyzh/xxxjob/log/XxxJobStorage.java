package com.bat.jyzh.xxxjob.log;

import com.xxl.job.core.biz.model.LogParam;
import com.xxl.job.core.biz.model.LogResult;
import com.xxl.job.core.biz.model.TriggerParam;

/**
 * 负责日志存储
 *
 * @author ZhengYu
 * @version 1.0 2021/5/16 0:15
 **/
public interface XxxJobStorage {

    /**
     * 存储日志
     *
     * @param triggerParam 执行调度的参数
     * @param logDetail    日志详情
     * @author ZhengYu
     */
    void storageJobLog(TriggerParam triggerParam, String logDetail);

    /**
     * 加载日志
     *
     * @param logParam 获取日志的参数
     * @author ZhengYu
     */
    LogResult loadJobLog(LogParam logParam);
}
