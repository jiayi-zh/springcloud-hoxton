package com.bat.jyzh.springcloudhoxton.hystrix.nativeapi;

import com.bat.jyzh.springcloudhoxton.hystrix.hystrixcommand.CustomFailHystrixCommand;
import com.bat.jyzh.springcloudhoxton.hystrix.hystrixcommand.CustomHystrixCommand;
import com.bat.jyzh.springcloudhoxton.hystrix.hystrixcommand.CustomHystrixObservableCommand;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import rx.Observable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 执行 {@link com.netflix.hystrix.HystrixCommand} 的四种方式 -> 归根到底都是使用 .toObservable() 方式
 *
 * @author ZhengYu
 * @version 1.0 2021/4/25 14:13
 **/
@Slf4j
@SpringBootTest
public class HystrixCommandTests {

    /**
     * .execute() 方式执行
     *
     * @author ZhengYu
     */
    @Test
    void hystrixCommandExecute() {
        String str1 = new CustomHystrixCommand("World").execute();
        System.out.println(str1);

        String str2 = new CustomFailHystrixCommand("World").execute();
        log.info("执行异常 Command 走降级方法返回: {}", str2);
    }


    /**
     * .queue() 方式执行
     *
     * @author ZhengYu
     */
    @Test
    void hystrixCommandQueue() throws InterruptedException, ExecutionException {
        Future<String> future = new CustomHystrixCommand("World").queue();
        System.out.println(future.get());
    }

    /**
     * .observe() 方式执行
     *
     * @author ZhengYu
     */
    @Test
    void hystrixCommandObserve() throws InterruptedException {
        // HystrixCommand<T>
        Observable<String> observable1 = new CustomHystrixCommand("World").observe();
        // .observe() 后立即执行
        log.info("HystrixCommand<T>#observe() 执行之后, command run ... ");
        Thread.sleep(1000);
        observable1.subscribe(s -> log.info("observable1 通过订阅，获取执行结果：{}", s));

        // HystrixObservableCommand<T>
        Observable<String> observable2 = new CustomHystrixObservableCommand("Bob").observe();
        // .observe() 后立即执行
        log.info("HystrixObservableCommand<T>#observe() 执行之后, command run ... ");
        Thread.sleep(1000);
        observable2.subscribe(s -> log.info("observable2 通过订阅，获取执行结果：{}", s));
    }

    /**
     * .observe() 方式执行
     *
     * @author ZhengYu
     */
    @Test
    void hystrixCommandToObservable() throws InterruptedException {
        // HystrixCommand<T>
        Observable<String> observable1 = new CustomHystrixCommand("World").toObservable();
        log.info("HystrixCommand<T>#observe() 执行之后, command not run ... ");
        Thread.sleep(1000);
        // .subscribe() 后才会执行
        observable1.subscribe();

        // HystrixObservableCommand<T>
        Observable<String> observable2 = new CustomHystrixObservableCommand("Bob").toObservable();
        log.info("HystrixObservableCommand<T>#observe() 执行之后, command run ... ");
        Thread.sleep(1000);
        // .subscribe() 后才会执行
        observable2.subscribe();
    }

}
