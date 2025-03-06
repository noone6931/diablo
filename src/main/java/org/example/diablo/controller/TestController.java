package org.example.diablo.controller;

import io.protostuff.Request;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/test")
public class TestController {


    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    @GetMapping("/test")
    public void test(){
        AtomicInteger ai = new AtomicInteger(0);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 3000; i++) {
            list.add(i+"");
        }
        int size = list.size();
        int batchSize = 1000;

        for (int i = 0; i < size; i+=batchSize) {
            int end = Math.min(i + batchSize, size);
            List<String> strings = list.subList(i, end);
            taskExecutor.execute(()->{
                for (String string : strings) {
                    ai.incrementAndGet();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        Collections.emptyMap();
        new HashMap<>();
        System.out.println(ai.get());
    }
}
