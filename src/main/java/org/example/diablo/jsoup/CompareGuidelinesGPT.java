package org.example.diablo.jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CompareGuidelinesGPT {

    // 请替换为您的 API Key 和 API URL
    public static final String API_KEY = "sk-qgvqrmsyafpayelkbgcmcjnndotnxanuwfwxleazbjbiejjf";
    public static final String API_URL = "https://api.siliconflow.cn/v1/chat/completions";
    public static final HttpClient httpClient = HttpClient.newHttpClient();

    public static  Integer FAIL_COUNT = 0;
    public static final Integer MAX_FAIL_COUNT =4;
    // 定义返回结果结构
    public static class MatchingResult {
        public List<String> sonarRules;
        public double score;
        public String remark;
        public MatchingResult(List<String> sonarRules, double score, String remark) {
            this.sonarRules = sonarRules;
            this.score = score;
            this.remark = remark;
        }
    }

    /**
     * 调用大模型 API，对当前规范进行比对。
     * 发送两条消息：第一条系统消息包含全量 Sonar 规则，
     * 第二条用户消息包含当前规范内容。
     */
    public static MatchingResult getMatchingSonarRules(String guideline, String sonarRulesContext) {
        // 构造消息数组
        JSONArray messages = new JSONArray();
        // 系统消息：全量 Sonar 规则（只发送一次，全量文本）
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", sonarRulesContext);
        messages.add(systemMessage);
        // 用户消息：当前规范
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", "请根据上述 Sonar 规则，找出最符合下面规范的规则，并给出匹配分数（0~5）和说明原因（中文回答，限制150token）：\n" + guideline
                + "\n\n请只以严格的 JSON字符串，不需要做```json这种格式， 格式返回结果，格式如下：\n"
                + "{ \"sonarRules\": [\"rule1\", \"rule2\"], \"score\": number, \"remark\": \"...\" }\n不要返回其他任何内容。");
        messages.add(userMessage);
        // 构造请求 JSON
        JSONObject payload = new JSONObject();
        payload.put("model", "Qwen/Qwen2.5-72B-Instruct-128K");
        payload.put("messages", messages);
        payload.put("temperature", 0);
        payload.put("max_tokens", 500);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(payload.toJSONString()))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = JSONObject.parseObject(response.body());
            JSONArray choices = jsonResponse.getJSONArray("choices");

            // 如果返回为空，则重试3次
            int retryCount = 0;
            while ((choices == null || choices.isEmpty()) && retryCount < 3) {
                choices = reSend(request);  // 重新发送请求，获取返回结果
                retryCount++;
            }
            if (choices == null || choices.isEmpty()) {
                System.err.println("调用 ChatGPT API 失败，对于规范：" + guideline);
                FAIL_COUNT  = FAIL_COUNT +1;
                return new MatchingResult(new ArrayList<>(), 0, "");
            }
            JSONObject choice = choices.getJSONObject(0);
            JSONObject message = choice.getJSONObject("message");
            String content = message.getString("content").trim();
            // 解析返回的 JSON 格式结果
            JSONObject resultObj;
            try {
                resultObj = JSONObject.parseObject(content);
            }catch (JSONException e){
                return new MatchingResult(new ArrayList<>(), 0, content);
            }
            JSONArray sonarRulesArray = resultObj.getJSONArray("sonarRules");
            List<String> sonarRules = new ArrayList<>();
            if (sonarRulesArray != null) {
                for (int i = 0; i < sonarRulesArray.size(); i++) {
                    sonarRules.add(sonarRulesArray.getString(i));
                }
            }
            double score = resultObj.getDoubleValue("score");
            String remark = resultObj.getString("remark");
            return new MatchingResult(sonarRules, score, remark);
        } catch (IOException | InterruptedException e) {
            System.err.println("调用 API 失败，对于规范：" + guideline);
            e.printStackTrace();
        }
        return new MatchingResult(new ArrayList<>(), 0, "");
    }

    private static JSONArray reSend(HttpRequest request) throws IOException, InterruptedException {
        JSONObject jsonResponse;
        HttpResponse<String> response;
        JSONArray choices;
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        jsonResponse = JSONObject.parseObject(response.body());
        choices = jsonResponse.getJSONArray("choices");
        TimeUnit.SECONDS.sleep(1);
        return choices;
    }

    public static void main(String[] args) {
        // 添加更多规范……
        String inputExcel = "C:\\Users\\CHENG\\IdeaProjects\\diablo\\ComparisonStart.xlsx";
        String outputExcel = "ComparisonStartUpdate0.xlsx";

        List<String> guidelines = new ArrayList<>();
        List<String> sonarRules = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(new File(inputExcel))) {
            // 读取 sheet1 中的所有规范
            Sheet sheetGuidelines = workbook.getSheetAt(0);
            for (int i = 1; i <= sheetGuidelines.getLastRowNum(); i++) {
                Row row = sheetGuidelines.getRow(i);
                if (row == null) continue;
                Cell cell = row.getCell(0);
                if (cell == null) continue;
                Cell cell1 = row.getCell(2);
                if (cell1 == null) {
                    continue;
                }
                double numericCellValue = cell1.getNumericCellValue();
                if (numericCellValue != 0) {
                    continue;
                }
                String guideline = cell.getStringCellValue().trim();
                if (!guideline.isEmpty()) {
                    guidelines.add(guideline);
                }
            }
            // 读取 sheet2 中的所有 Sonar 规则
            Sheet sheetSonar = workbook.getSheetAt(1);
            for (int i = 0; i <= sheetSonar.getLastRowNum(); i++) {
                Row row = sheetSonar.getRow(i);
                if (row == null) continue;
                Cell cell = row.getCell(0);
                if (cell == null) continue;
                String rule = cell.getStringCellValue().trim();
                if (!rule.isEmpty()) {
                    sonarRules.add(rule);
                }
            }
        } catch (Exception e) {
            System.err.println("读取 Excel 文件出错：");
            e.printStackTrace();
            return;
        }
        // 将所有 Sonar 规则拼接成一个全量字符串，每条规则以换行符分隔
        StringBuilder sonarRulesTextBuilder = new StringBuilder();
        for (String rule : sonarRules) {
            sonarRulesTextBuilder.append(rule).append("\n");
        }
        String sonarRulesText = sonarRulesTextBuilder.toString();

        // 遍历每条我们的规范，调用 ChatGPT API
        List<MatchingResult> results = new ArrayList<>();
        // 遍历每个规范调用 API
        for (String guideline : guidelines) {
            if (FAIL_COUNT >= MAX_FAIL_COUNT){
                break;
            }
            MatchingResult result = getMatchingSonarRules(guideline, sonarRulesText);
            System.out.println("规范: " + guideline);
            System.out.println("匹配的 Sonar 规则: " + String.join("; ", result.sonarRules));
            System.out.println("匹配分数: " + result.score);
            System.out.println("备注: " + result.remark);
            System.out.println("-------------------------------------------------");
            results.add(result);
            try {
                Thread.sleep(1000);  // 延时，防止速率限制
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // 将比对结果写入新的 Excel 文件
        Workbook outputWorkbook = new XSSFWorkbook();
        Sheet outputSheet = outputWorkbook.createSheet("Comparison");
        int rowIndex = 0;
        Row header = outputSheet.createRow(rowIndex++);
        header.createCell(0).setCellValue("我们的规范");
        header.createCell(1).setCellValue("匹配的 Sonar 规则");
        header.createCell(2).setCellValue("匹配分数(0~5)");
        header.createCell(3).setCellValue("备注");

        for (int i = 0; i < results.size(); i++) {
            String guideline = guidelines.get(i);
            MatchingResult result = results.get(i);
            Row row = outputSheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(guideline);
            if (!result.sonarRules.isEmpty()) {
                row.createCell(1).setCellValue(String.join("; ", result.sonarRules));
            }
            row.createCell(2).setCellValue(result.score);
            row.createCell(3).setCellValue(result.remark);
        }
        try (FileOutputStream fos = new FileOutputStream(outputExcel)) {
            outputWorkbook.write(fos);
            outputWorkbook.close();
        } catch (Exception e) {
            System.err.println("写入 Excel 文件出错：");
            e.printStackTrace();
        }
        System.out.println("比对结果已输出到 " + outputExcel);
    }
}