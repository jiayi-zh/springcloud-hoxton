package com.bat.jyzh.cache.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link com.google.common.cache.CacheBuilder} 本地缓存使用
 *
 * @author ZhengYu
 * @version 1.0 2021/7/13 15:34
 **/
@Slf4j
public class GuavaCacheApi {

    private static final AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) {
        LoadingCache<String, Integer> readWriteCacheMap =
                CacheBuilder.newBuilder().initialCapacity(1000)
                        .expireAfterWrite(5, TimeUnit.SECONDS)
                        .removalListener((RemovalListener<String, Integer>) removalNotification ->
                                log.info("缓存(key: {}, value: {})移除, 原因: {}",
                                        removalNotification.getKey(),
                                        removalNotification.getValue(),
                                        removalNotification.getCause()))
                        .build(new CacheLoader<String, Integer>() {
                            @Override
                            public Integer load(String str) {
                                int count = counter.incrementAndGet();
                                log.info("缓存{}不存在, 尝试获取值: {}", str, count);
                                return count;
                            }
                        });

        readWriteCacheMap.put("k1", counter.get());
//        readWriteCacheMap.get("k2");

//        new Thread(() -> {
//            try {
//                Thread.sleep(2000);
//                log.info("即将刷新缓存");
//                readWriteCacheMap.refresh("k1");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(10000);
                readWriteCacheMap.get("k1");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
