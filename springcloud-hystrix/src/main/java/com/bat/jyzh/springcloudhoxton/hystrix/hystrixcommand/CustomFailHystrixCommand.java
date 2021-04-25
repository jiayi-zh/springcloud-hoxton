package com.bat.jyzh.springcloudhoxton.hystrix.hystrixcommand;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * {@link HystrixCommand} 实现
 *
 * @author ZhengYu
 * @version 1.0 2021/4/25 16:57
 **/
public class CustomFailHystrixCommand extends HystrixCommand<String> {

    private final String name;

    public CustomFailHystrixCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        throw new RuntimeException("Command 报错, 需要走降级方法");
    }

    @Override
    protected String getFallback() {
        return "Hello Failure " + name;
    }
}
