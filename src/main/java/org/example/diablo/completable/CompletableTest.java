package org.example.diablo.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            System.out.println("1:runAsync");
        });

        CompletableFuture<String> stringCompletableFuture1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5); // 延迟任务完成时间
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "3:??";
        });

        CompletableFuture<String> stringCompletableFuture = stringCompletableFuture1.thenApply(asd -> {
            System.out.println("2:" + asd);
            try {
                TimeUnit.SECONDS.sleep(5); // 模拟耗时操作
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "thenApply";
        });

        runAsync.join();

        // 在 `stringCompletableFuture1` 完成之前调用 `complete`
        boolean complete = stringCompletableFuture.complete("123123123");
        System.out.println("Complete called: " + complete);

        // 获取最终结果
        System.out.println("Result: " + stringCompletableFuture.get());
    }
}
