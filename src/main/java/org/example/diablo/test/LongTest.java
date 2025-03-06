package org.example.diablo.test;

public class LongTest {
    public static void main(String[] args) {
        int a = 1024*1024*50;
        System.out.println(Integer.MAX_VALUE);
        System.out.println(a);


        double b = Math.ceil(1024/23);
        System.out.println(b);


        String str = "123123123123";
        String a1 = str.replace("1", "a");
        System.out.println(str);
        System.out.println(a1);
    }
}
