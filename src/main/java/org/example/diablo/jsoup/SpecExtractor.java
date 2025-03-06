package org.example.diablo.jsoup;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecExtractor {
    // 正则表达式匹配形如 "2.1.1.1. " 的编号开头
    private static final Pattern NUMBERING_PATTERN = Pattern.compile("^(\\d+(?:\\.\\d+)*\\.)\\s*(.*)$");

    public static void main(String[] args) {
        // Excel 输出文件名
        String outputExcel = "规范.xlsx";
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
            // 通过 Jsoup 抓取网页（建议设置适当的超时与请求头，如有需要）
//            Document doc = Jsoup.connect(url).get();
            // 提取所有段落（<p>标签）的文本
            // 直接选取所有 h4 标签
            Elements headers = doc.select("h4");
            List<String> specs = new ArrayList<>();

            for (Element header : headers) {
                String text = header.text().trim();
                if (text.isEmpty()) {
                    continue;
                }
                // 当遇到后续章节（例如以 "3." 开头）时退出目标部分
                if (text.matches("^3\\.\\s*.*$")) {
                    break;
                }
                // 对编号格式进行处理
                Matcher matcher = NUMBERING_PATTERN.matcher(text);
                if (matcher.find()) {
                    String numbering = matcher.group(1);  // 编号部分，如 "2.1.1.1."
                    String content = matcher.group(2);      // 编号后的正文
                    // 计算编号层级（“.”数量），层级 >= 4 时只保留正文内容
                    int level = numbering.split("\\.").length - 1;
                    if (level >= 4) {
                        specs.add(content);
                    } else {
                        specs.add(text);
                    }
                } else {
                    // 非编号格式文本视为上一条规范的补充说明
                    if (!specs.isEmpty()) {
                        String prev = specs.remove(specs.size() - 1);
                        specs.add(prev + " " + text);
                    }
                }
            }

            // 利用 Apache POI 将提取结果写入 Excel 文件
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("规范");
            int rowIndex = 0;
            for (String spec : specs) {
                Row row = sheet.createRow(rowIndex++);
                Cell cell = row.createCell(0);
                cell.setCellValue(spec);
            }
            // 自动调整列宽
            sheet.autoSizeColumn(0);
            try (FileOutputStream fos = new FileOutputStream(outputExcel)) {
                workbook.write(fos);
            }
            workbook.close();
            System.out.println("Excel 文件已生成：" + outputExcel);

        } catch (IOException e) {
            // 实话实说地输出异常信息
            System.err.println("程序执行过程中出现错误：");
            e.printStackTrace();
        }
    }
}
