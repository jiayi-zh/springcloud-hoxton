package com.bat.jyzh.springcloudhoxton.hystrix.hystrixcommand;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * {@link com.netflix.hystrix.HystrixCommand} 实现
 *
 * @author ZhengYu
 * @version 1.0 2021/4/25 16:57
 **/
public class CustomHystrixCommand extends HystrixCommand<String> {

    private final String name;

    public CustomHystrixCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "Hello " + name + "!";
    }
}
