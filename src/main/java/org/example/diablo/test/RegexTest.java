package org.example.diablo.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    public static void main(String[] args) {
//        String regex = "http[s]?://(?:http[s]?|[a-zA-Z]|[0-9]|[$-_@.&+]|[!*\\\\(\\\\),]|(?:%[0-9a-fA-F][0-9a-fA-F]))+";
        String regex = "http[s]?://(?:http[s]?|[a-zA-Z0-9$-_@.&+!*\\\\(\\\\),]|%[0-9a-fA-F]{2})++";
        StringBuilder input = new StringBuilder("http://");

        for (int i = 0; i < 100000; i++) {
            input.append("a"); // 模拟无效的长输入
        }
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input.toString());
            while (matcher.find()) {
                System.out.println("Match found!");
            }
            System.out.println("No issues.");
        } catch (StackOverflowError e) {
            System.err.println("StackOverflowError detected!");
            e.printStackTrace();
        }
    }
}
