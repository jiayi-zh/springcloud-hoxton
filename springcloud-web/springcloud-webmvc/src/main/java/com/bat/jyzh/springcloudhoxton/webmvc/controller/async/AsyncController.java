package com.bat.jyzh.springcloudhoxton.webmvc.controller.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异步任务
 *
 * @author ZhengYu
 * @version 1.0 2021/5/22 11:01
 **/
@Slf4j
@RestController
@RequestMapping("/async")
public class AsyncController {

    /**
     * 使用 DeferredResult 处理结果
     * <p>
     * eg: http://127.0.0.1:8080/async/defer/result?errFlag=true
     */
    @GetMapping("/defer/result")
    public DeferredResult<String> testAsyncDeferredResult(@RequestParam(value = "errFlag") boolean errFlag) {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        new Thread(() -> {
            doBusiness();
            if (errFlag) {
                deferredResult.setErrorResult(new RuntimeException("异步任务执行失败"));
            } else {
                deferredResult.setResult("异步任务执行成功");
            }
        }).start();
        return deferredResult;
    }

    @GetMapping("completableFuture")
    public CompletableFuture<String> completableFuture(HttpServletRequest httpServletRequest) {
        // 线程池一般不会放在这里，会使用static声明，这只是演示
        ExecutorService executor = Executors.newCachedThreadPool();
        System.out.println(LocalDateTime.now().toString() + "--->主线程开始");
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            doBusiness();
            return "success";
        }, executor);
        System.out.println(LocalDateTime.now().toString() + "--->主线程结束");
        return completableFuture;
    }

    @RequestMapping("/handle")
    public ResponseEntity<String> handle() throws URISyntaxException {
        URI location = new URI("file://foo/bar");
        /*
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(location);
            responseHeaders.set("MyResponseHeader", "MyValue");
            return new ResponseEntity<>("Hello World", responseHeaders, HttpStatus.CREATED);
        */
        return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body("Hello World");
    }

    @RequestMapping("/streamingResponseBody")
    public StreamingResponseBody streamingResponseBody() {
        return outputStream -> Executors.newSingleThreadExecutor().submit(() -> {
            try {
                String path = "D:\\tomcat\\apache-tomcat-8.5.66.zip";
                FileInputStream fileInputStream = new FileInputStream(path);
                byte[] buf = new byte[1];
                while (fileInputStream.read(buf) != -1) {
                    outputStream.write(buf);
                }
            } catch (Exception ignore) {

            }
        });
    }

    private void doBusiness() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
