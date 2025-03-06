package org.example.diablo.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Demo {

    private Integer id = 10000;
    private Object object = new Object();

    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.test();
    }
    public void  test(){
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 10000; i++) {
            executorService.execute(()->{
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (object){
                    id--;
                    System.out.println(id);
                }
            });
        }
    }
}
