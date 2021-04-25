package com.bat.jyzh.springcloudhoxton.hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * {@link com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand } 注解使用及源码实现
 *
 * @author ZhengYu
 * @version 1.0 2021/4/25 14:51
 **/
@RestController
@RequestMapping("/hystrix")
public class HystrixCommandController {

    private static final Random RANDOM = new Random();


    @HystrixCommand(fallbackMethod = "doSomethingFallBack")
    @GetMapping("/test")
    public String doSomething() {
        if (RANDOM.nextBoolean()) {
            throw new RuntimeException("运行时异常");
        }
        return "success";
    }

    private String doSomethingFallBack() {
        return "doSomething fallback";
    }
}
