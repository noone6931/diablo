package org.example.diablo.jsoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

public class JsoupLogin {
    public static void main(String[] args) {

    }

    public static void login() {
        try {
            // 1. 执行登录请求，获取登录后会话的 Cookie
            Map<String, String> loginCookies = Jsoup.connect("https://myrdc.yljr.com/yunyi/login.action")
                    // 设置表单参数：用户名、密码及登录按钮（根据实际情况调整）
                    .data("os_username", "chengming")
                    .data("os_password", "123456")
                    .data("login", "登录")
                    // 指定使用 POST 方法
                    .method(Connection.Method.POST)
                    // 模拟浏览器的 User-Agent（有时对登录有效果）
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36")
                    .execute()
                    .cookies();

            // 2. 使用登录后获得的 Cookie 访问目标页面
            Document doc = Jsoup.connect("https://myrdc.yljr.com/yunyi/pages/viewpage.action?pageId=334145")
                    .cookies(loginCookies)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36")
                    .get();

            // 输出页面标题以确认是否成功访问
            System.out.println("页面标题：" + doc.title());

            // 在此处可继续后续内容的解析及处理逻辑，例如抽取需要的规范信息、生成 Excel 等。

        } catch (IOException e) {
            System.err.println("登录或页面访问过程中出现错误：");
            e.printStackTrace();
        }
    }
}
