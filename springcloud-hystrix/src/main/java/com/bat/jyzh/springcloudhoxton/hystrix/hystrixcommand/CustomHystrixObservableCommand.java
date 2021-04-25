package com.bat.jyzh.springcloudhoxton.hystrix.hystrixcommand;


import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * {@link com.netflix.hystrix.HystrixObservableCommand}
 *
 * @author ZhengYu
 * @version 1.0 2021/4/25 17:00
 **/
public class CustomHystrixObservableCommand extends HystrixObservableCommand<String> {

    private final String name;

    public CustomHystrixObservableCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected Observable<String> construct() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        // a real example would do work like a network call here
                        observer.onNext("Hello");
                        observer.onNext(name + "!");
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }
}
