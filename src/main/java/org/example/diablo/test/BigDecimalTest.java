package org.example.diablo.test;

import java.math.BigDecimal;

public class BigDecimalTest {

    public static void main(String[] args) {
        double a = 0.1;
        BigDecimal bigDecimal = new BigDecimal(a);
        System.out.println(bigDecimal);

        BigDecimal bigDecimal1 = BigDecimal.valueOf(a);
        System.out.println(bigDecimal1);
    }
}
