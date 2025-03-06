package org.example.diablo.oomTest;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinKeepAliveTest {
    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool(4);

        // 提交一个简单任务
        for (int i = 0; i < 10; i++) {
            pool.submit(() -> {
                System.out.println("Task executed by: " + Thread.currentThread().getName());
            });
        }

        // 等待任务完成
        Thread.sleep(1000);

        System.out.println("Pool size before idle1: " + pool.getPoolSize());

        Thread.sleep(1000);

        System.out.println("Pool size before idle2: " + pool.getPoolSize());

        // 等待超过 60 秒，观察线程是否被回收
        Thread.sleep(60000);

        System.out.println("Pool size after idle: " + pool.getPoolSize());

        pool.shutdown();
    }
}
