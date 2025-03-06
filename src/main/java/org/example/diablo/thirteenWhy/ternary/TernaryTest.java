package org.example.diablo.thirteenWhy.ternary;

import org.apache.tomcat.util.descriptor.web.JspConfigDescriptorImpl;

public class TernaryTest {
    public static void main(String[] args) {
        int x = 5;
        double y = 2.0;
        Object result =x>0?x:y;
        System.out.println(result);
    }
}
