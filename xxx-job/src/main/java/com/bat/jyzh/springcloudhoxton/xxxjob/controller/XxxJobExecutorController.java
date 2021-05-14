package com.bat.jyzh.springcloudhoxton.xxxjob.controller;

import com.alibaba.fastjson.JSONObject;
import com.xxl.job.core.biz.model.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author ZhengYu
 * @version 1.0 2021/5/14 11:45
 **/
@Slf4j
@RestController
@RequestMapping("/xxx-job")
public class XxxJobExecutorController {

    @Value("${custom.schedulingCenter.rootAddr}")
    private String rootAddr;

//    @PostMapping("/beat")
//    public ReturnT<String> beat() {
//        log.info("调度中心向执行器发送了心跳 ... ");
//        return ReturnT.SUCCESS;
//    }
//
//    @PostMapping("/idleBeat")
//    public ReturnT<String> idleBeat(@RequestBody IdleBeatParam body) {
//        log.info("调度中心向执行器发送了忙碌检测, 详情: {}", JSONObject.toJSONString(body));
//        return ReturnT.SUCCESS;
//    }

    private OkHttpClient okHttpClient = new OkHttpClient();
    private MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    @PostMapping("/run")
    public ReturnT<String> idleBeat(@RequestBody TriggerParam body) {
        String msg = JSONObject.toJSONString(body);
        log.info("调度中心向执行器发送了执行任务请求, 详情: {}", msg);

        // log
        logDB.put(body.getLogId(), msg);

        // callback
        new Thread(() -> {
            HandleCallbackParam param = new HandleCallbackParam();
            param.setLogId(body.getLogId());
            param.setExecuteResult(ReturnT.SUCCESS);
            Request request = new Request.Builder()
                    .header("Content-Type", "application/json")
                    .url(rootAddr + "/callback")
                    .post(okhttp3.RequestBody.create(mediaType, JSONObject.toJSONBytes(new ArrayList<HandleCallbackParam>(1) {{
                        add(param);
                    }})))
                    .build();
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(10));

                Response execute = okHttpClient.newCall(request).execute();
                if (!execute.isSuccessful()) {
                    log.error("########################## {}", execute.message());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        return ReturnT.SUCCESS;
    }

//    @PostMapping("/kill")
//    public ReturnT<String> idleBeat(@RequestBody KillParam body) {
//        log.info("调度中心向执行器发送了关闭任务请求, 详情: {}", JSONObject.toJSONString(body));
//        return ReturnT.SUCCESS;
//    }

    @PostMapping("/log")
    public ReturnT<LogResult> log(@RequestBody LogParam body) {
        log.info("调度中心向执行器发送了日志查询请求, 详情: {}", JSONObject.toJSONString(body));
        LogResult logResult = new LogResult();
        logResult.setFromLineNum(body.getFromLineNum());
        logResult.setToLineNum(1);
        logResult.setLogContent(logDB.get(body.getLogId()));
        ReturnT<LogResult> ret = new ReturnT<>();
        ret.setCode(200);
        ret.setContent(logResult);
        return ret;
    }

    private Map<Long, String> logDB = new HashMap<>();
}
