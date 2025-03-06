package org.example.diablo.test;

import java.math.BigDecimal;
import java.util.Random;

public class RandomTest {
    public static void main(String[] args) {
        // 只new一次Random对象的情况
        Random rand1 = new Random();
        for (int i = 0; i < 5; i++) {
            System.out.println("只new一次Random：" + rand1.nextInt());
        }

        // 每次循环都new Random对象的情况
        for (int i = 0; i < 5; i++) {
            Random rand2 = new Random();
            System.out.println("每次循环new Random：" + rand2.nextInt());
        }
//        int a = 2;
//            assert a<0:"assert";
//
//        System.out.println("finish");

        double d = 0.1;
        BigDecimal bd = new BigDecimal(d);
        System.out.println(bd);
    }
}
