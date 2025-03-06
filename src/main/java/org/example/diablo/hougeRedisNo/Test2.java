package org.example.diablo.hougeRedisNo;

public class Test2 {

    public static void main(String[] args) {
        int number1 = 0;      // 输入的整数
        int number2 = 100;    // 输入的整数
        int number3 = 123;    // 输入的整数
        int number4 = 1234;   // 输入的整数

        // 输出结果
        System.out.println(padWithZeros(number1)); // 00000
        System.out.println(padWithZeros(number2)); // 00100
        System.out.println(padWithZeros(number3)); // 00123
        System.out.println(padWithZeros(number4)); // 01234
    }
    /**
     * 将整数格式化为固定长度（5位）的字符串，不足部分用0填充
     * @param number 输入的整数
     * @return 格式化后的字符串
     */
    public static String padWithZeros(int number) {
        return String.format("%05d", number); // %05d 表示整数总长度为5位，不足用0补齐
    }
}

