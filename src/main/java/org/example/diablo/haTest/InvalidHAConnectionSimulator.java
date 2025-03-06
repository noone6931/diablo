package org.example.diablo.haTest;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class InvalidHAConnectionSimulator {
    public static void main(String[] args) {
        String host = "127.0.0.1";  // RocketMQ HA Service 主机
        int port = 10912;           // RocketMQ HA Service 端口
        int connectionCount = 100000; // 设定模拟的连接次数

//        for (int i = 0; i < connectionCount; i++) {
        int i = 0;
        while(true){
            try {
                // 建立到 HAService 的 TCP 连接
                i++;
                Socket socket = new Socket(host, port);
                System.out.println("Connected to RocketMQ HAService on attempt: " + (i));

                // 不发送任何有效数据，立即断开连接
                socket.close();
                System.out.println("Closed connection to RocketMQ HAService on attempt: " + (i));

            } catch (IOException e) {
                e.printStackTrace();
            }

            // 可选：设置延迟，避免连接过于频繁
            try {
                Thread.sleep(100);  // 每次连接后延迟 100 毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        System.out.println("Frequent connection simulation completed.");
    }
}
