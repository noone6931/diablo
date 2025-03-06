package org.example.diablo.oomTest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RequestMapping("/testOOM")
@RestController
public class TestOOMController {

    @GetMapping("/test")
    public void test() {
        List<byte[]> list = new ArrayList<>();
//        try{
        for (int i = 0; i < 100; i++) {
            byte[] bytes = new byte[1024 * 1024];
            list.add(bytes);
        }
//        }catch (Throwable e){
//            e.printStackTrace();
//        }

    }

    @GetMapping("/testCompletableFuture")
    public void testCompletableFuture(){
        AtomicInteger ai = new AtomicInteger(0);
        List<byte[]> list = new ArrayList<>();
        CompletableFuture.runAsync(()->{
            while(true){
                try{
                    ai.incrementAndGet();
                    if (ai.get()> 10000){
                        for (int i = 0; i < 100; i++) {
                            byte[] bytes = new byte[1024 * 1024];
                            list.add(bytes);
                        }
                    }
                    System.out.println(Thread.currentThread().getName());
                }catch (Throwable e){
                    e.printStackTrace();
                }
//                try {
//                    TimeUnit.MILLISECONDS.sleep(500);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            }
        });
    }

    @GetMapping("/testCompletableFuture2")
    public void testCompletableFuture2(){
        AtomicInteger ai = new AtomicInteger(0);
        List<byte[]> list = new ArrayList<>();
        CompletableFuture.runAsync(()->{
//            while(true){

//                try {
//                    TimeUnit.MILLISECONDS.sleep(500);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
