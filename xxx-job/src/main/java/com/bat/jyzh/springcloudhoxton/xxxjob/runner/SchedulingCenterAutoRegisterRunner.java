package com.bat.jyzh.springcloudhoxton.xxxjob.runner;

import com.alibaba.fastjson.JSONObject;
import com.bat.jyzh.springcloudhoxton.xxxjob.dto.RegistryDTO;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author ZhengYu
 * @version 1.0 2021/5/14 13:22
 **/
@Component
public class SchedulingCenterAutoRegisterRunner implements ApplicationRunner {

    @Value("${custom.schedulingCenter.rootAddr}")
    private String rootAddr;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        OkHttpClient okHttpClient = new OkHttpClient();
        RegistryDTO registryDTO = new RegistryDTO();
        registryDTO.setRegistryGroup("EXECUTOR");
        registryDTO.setRegistryKey("xxl-job-executor-custom");
        registryDTO.setRegistryValue("http://192.168.9.27:8081/xxx-job/");

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        byte[] bytes = JSONObject.toJSONBytes(registryDTO);
        Request request = new Request.Builder()
                .header("Content-Type","application/json")
                .url(rootAddr + "/registry")
                .post(RequestBody.create(mediaType, bytes))
                .build();

        Thread thread = new Thread(() -> {
            while (true) {
                try (Response resp = okHttpClient.newCall(request).execute()) {
                    if (!resp.isSuccessful()) {
                        System.out.println("注册/保活 失败" + resp.message());
                    }

                    Thread.sleep(TimeUnit.SECONDS.toMillis(10));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.setName("SchedulingCenterAutoRegisterRunner");
        thread.start();
    }
}
