package org.example.diablo.md5;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Test {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // 输入密码
        String input = "test1234";

        // 创建 MD5 MessageDigest 实例
        MessageDigest md = MessageDigest.getInstance("MD5");

        // 获取字节数组
        byte[] messageDigest = md.digest(input.getBytes());

        // 转换字节数组为十六进制格式的字符串
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            // 转换每个字节
            hexString.append(Integer.toHexString(0xFF & b));
        }

        // 输出 MD5 哈希值
        System.out.println("MD5 Hash of 'test1234': " + hexString.toString());

    }
}
