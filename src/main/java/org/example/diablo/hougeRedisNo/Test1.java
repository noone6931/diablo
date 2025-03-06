package org.example.diablo.hougeRedisNo;

import io.vertx.core.impl.ConcurrentHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class Test1 {
    private static final ConcurrentHashMap<String, AtomicLong> timestampCounters = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(Test1.class);

    public static void main(String[] args) throws InterruptedException {
        Test1 test1 = new Test1();
        Date date = new Date();

        String serialNo = test1.getSerialNo(String.valueOf(date.getTime()));
        String serialNo1 = test1.getSerialNo(String.valueOf(date.getTime()));


//        System.out.println("serialNo: " + serialNo);
//        System.out.println("serialNo1: " + serialNo1);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 1000; i++) {
            Set<String> set = new ConcurrentHashSet<>();
            executorService.submit(()->{
                String serialNo2 = null;
                try {
                    serialNo2 = test1.getSerialNo(String.valueOf(date.getTime()));
                    if (set.contains(serialNo2)) {
                        log.info("重复！, {}", set);
                    }else {
                        set.add(serialNo2);
                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("thread:{}, serialNo:{}", Thread.currentThread().getName(), serialNo2);
            });
        }
//        executorService.wait();
        Thread.sleep(10_000);
        executorService.shutdown();

    }

    public String getSerialNo(String currentTimestamp) throws InterruptedException {
        // 获取或初始化当前时间戳的计数器
        AtomicLong counter = timestampCounters.computeIfAbsent(currentTimestamp, key -> new AtomicLong(Long.parseLong(key)));
        Thread.sleep(1000);
        // 自增并返回序列号
        long serialNo = counter.incrementAndGet();
        return String.valueOf(serialNo);
    }



}
