package com.bat.jyzh.springcloudhoxton.hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand } 注解使用及源码实现
 *
 * @author ZhengYu
 * @version 1.0 2021/4/25 14:51
 **/
@Slf4j
@RestController
@RequestMapping("/hystrix")
public class HystrixCommandController {

    private static final AtomicInteger COUNTER = new AtomicInteger();

    private static final AtomicBoolean RESET_FLAG = new AtomicBoolean(false);

    @HystrixCommand(
            // 配置全局唯一标识服务分组的名称
            groupKey = "hystrixTestGroup",
            // 配置全局唯一标识服务的名称
            commandKey = "hystrixTestCommandKey",
            // 对线程池进行设定，细粒度的配置，相当于对单个服务的线程池信息进行设置，也可多个服务设置同一个threadPoolKey构成线程组
            threadPoolKey = "hystrixTestThreadPoolKey",
            // fallback 方法, 方法参数可以与 @HystrixCommand 修饰的方法一致
            fallbackMethod = "doSomethingFallBack",
            // Command 配置 配置可参考: https://github.com/Netflix/Hystrix/wiki/Configuration
            commandProperties = {
                    /*
                     * execution
                     */
                    // 隔离策略使用 THREAD
                    @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
                    // 隔离策略使用 SEMAPHORE
                    // @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
                    // 当隔离策略为 SEMAPHORE 使的最大并发数
                    // @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value = "1"),

                    // 方法执行超时时间
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),

                    /*
                     * Circuit Breaker
                     */
                    // 在一个窗口中失败 value 次会使 Circuit Breaker 打开
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    // 整个滑动窗口失败的百分比, 大于等于会使 Circuit Breaker 打开
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    // 在 Circuit Breaker 打开 value 毫秒后, 进入半开模式
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            },
            // 线程池的配置
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "10")
            },
            // ignoreExceptions = {},
            // 设置执行方式 eager - HystrixObservable.observe(), lazy - HystrixObservable.toObservable().
            observableExecutionMode = ObservableExecutionMode.EAGER
            // 不可忽略的异常
            // raiseHystrixExceptions = {}
            // 默认的 fallback 方法, 必须是无参且优先级比 fallbackMethod 低
            // defaultFallback = ""
    )
    @GetMapping("/test")
    public String doSomething() {
        log.info("Hystrix 服务降级测试 -> 第{}个请求到达", COUNTER.incrementAndGet());
        try {
            // 模拟因网络抖动导致的任务执行超时
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!RESET_FLAG.get() && RESET_FLAG.compareAndSet(false, true)) {
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("重置计数器, 重置结果: {}", RESET_FLAG.compareAndSet(true, false));
            }).start();
        }
        return "success";
    }

    // fallback 方法
    private String doSomethingFallBack() {
        return "doSomething fallback";
    }
}