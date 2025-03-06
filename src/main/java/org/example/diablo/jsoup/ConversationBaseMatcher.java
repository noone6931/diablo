package org.example.diablo.jsoup;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ConversationBaseMatcher {
    // 替换为您的 API Key 和 API URL
    public static final String API_KEY = "sk-qgvqrmsyafpayelkbgcmcjnndotnxanuwfwxleazbjbiejjf";
    public static final String API_URL = "https://api.siliconflow.cn/v1/chat/completions";
    public static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(60))
            .build();

    // 全量 Sonar 规则文本（从 sheet2 读取后拼接，这里以示例文本表示）
    public static final String sonarRulesContext = "规则1: .equals() should not be used to test the values of \"Atomic\" classes\n"
            + "规则2: \"=+\" should not be used instead of \"+=\"\n"
            // … 其余规则……
            + "规则381: Zero should not be a possible denominator";

    // 匹配结果数据结构
    public static class MatchingResult {
        public String guideline;         // 我们的规范
        public List<String> sonarRules;  // 匹配到的 Sonar 规则（可能多个）
        public double score;             // 匹配分数（0~5）
        public String remark;            // 备注说明

        public MatchingResult(String guideline, List<String> sonarRules, double score, String remark) {
            this.guideline = guideline;
            this.sonarRules = sonarRules;
            this.score = score;
            this.remark = remark;
        }
    }


    /**
     * 发送当前会话历史（包含系统消息和所有用户/助手消息）给大模型，
     * 返回大模型响应的 JSONObject。
     */
    public static JSONObject sendSonar(JSONArray conversationHistory) throws IOException, InterruptedException {
        JSONObject payload = new JSONObject();
        payload.put("model", "Qwen/Qwen2.5-72B-Instruct-128K");
        payload.put("messages", conversationHistory);
        payload.put("temperature", 0);
        payload.put("max_tokens", 300);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .timeout(Duration.ofSeconds(60))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(payload.toJSONString()))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return JSONObject.parseObject(response.body());
    }

    /**
     * 发送当前会话历史（包含系统消息和所有用户/助手消息）给大模型，
     * 返回大模型响应的 JSONObject。
     */
    public static JSONObject sendConversation(JSONArray conversationHistory) throws IOException, InterruptedException {
        JSONObject payload = new JSONObject();
        payload.put("model", "Qwen/Qwen2.5-72B-Instruct-128K");
        payload.put("messages", conversationHistory);
        payload.put("temperature", 0);
        payload.put("max_tokens", 300);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .timeout(Duration.ofSeconds(60))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(payload.toJSONString()))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return JSONObject.parseObject(response.body());
    }

    /**
     * 根据当前规范与会话历史调用大模型，获取匹配结果。
     * 会话历史中已包含全量 Sonar 规则的系统消息。
     */
    public static MatchingResult getMatchingForGuideline(String guideline, JSONArray conversationHistory) {
        // 添加当前规范作为用户消息
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", "请在上述 Sonar 规则中，找出最符合下面规范的规则，并给出匹配分数（0~5）和说明原因：\n" + guideline      + "\n\n请只以严格的 JSON字符串，不需要做```json这种格式， 格式返回结果，格式如下：\n"
                + "{ \"sonarRules\": [\"rule1\", \"rule2\"], \"score\": number, \"remark\": \"...\" }\n不要返回其他任何内容。");
        conversationHistory.add(userMessage);

        try {
            JSONObject jsonResponse = sendConversation(conversationHistory);
            JSONArray choices = jsonResponse.getJSONArray("choices");
            if (choices != null && !choices.isEmpty()) {
                JSONObject choice = choices.getJSONObject(0);
                JSONObject message = choice.getJSONObject("message");
                String content = message.getString("content").trim();
                // 解析大模型返回的 JSON 格式结果
                JSONObject resultObj = JSONObject.parseObject(content);
                JSONArray sonarRulesArray = resultObj.getJSONArray("sonarRules");
                List<String> sonarRules = new ArrayList<>();
                if (sonarRulesArray != null) {
                    for (int i = 0; i < sonarRulesArray.size(); i++) {
                        sonarRules.add(sonarRulesArray.getString(i));
                    }
                }
                double score = resultObj.getDoubleValue("score");
                String remark = resultObj.getString("remark");

                // 将大模型回复作为助手消息保存到会话历史，供后续参考
                JSONObject assistantMessage = new JSONObject();
                assistantMessage.put("role", "assistant");
                assistantMessage.put("content", content);
                conversationHistory.add(assistantMessage);

                return new MatchingResult(guideline, sonarRules, score, remark);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("调用大模型 API 失败，对于规范：" + guideline);
            e.printStackTrace();
        }
        return new MatchingResult(guideline, new ArrayList<>(), 0, "");
    }

    /**
     * 将所有匹配结果写入 Excel 文件。
     */
    public static void writeResultsToExcel(List<MatchingResult> results, String outputExcelPath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Comparison Results");
        int rowIndex = 0;
        Row header = sheet.createRow(rowIndex++);
        header.createCell(0).setCellValue("我们的规范");
        header.createCell(1).setCellValue("匹配的 Sonar 规则");
        header.createCell(2).setCellValue("匹配分数");
        header.createCell(3).setCellValue("备注");

        for (MatchingResult result : results) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(result.guideline);
            row.createCell(1).setCellValue(String.join("; ", result.sonarRules));
            row.createCell(2).setCellValue(result.score);
            row.createCell(3).setCellValue(result.remark);
        }

        // 自动调整列宽
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fos = new FileOutputStream(outputExcelPath)) {
            workbook.write(fos);
            workbook.close();
        } catch (IOException e) {
            System.err.println("写入 Excel 文件时发生错误：");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String inputExcel = "C:\\Users\\CHENG\\IdeaProjects\\diablo\\规范.xlsx";
        List<String> guidelines = new ArrayList<>();
        List<String> sonarRules = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(new File(inputExcel))) {
            // 读取 sheet1 中的所有规范
            Sheet sheetGuidelines = workbook.getSheetAt(1);
            for (int i = 0; i <= sheetGuidelines.getLastRowNum(); i++) {
                Row row = sheetGuidelines.getRow(i);
                if (row == null) continue;
                Cell cell = row.getCell(0);
                if (cell == null) continue;
                String guideline = cell.getStringCellValue().trim();
                if (!guideline.isEmpty()) {
                    guidelines.add(guideline);
                }
            }
            // 读取 sheet2 中的所有 Sonar 规则
            Sheet sheetSonar = workbook.getSheetAt(2);
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

        // 初始化会话历史，先添加系统消息（全量 Sonar 规则）
        JSONArray conversationHistory = new JSONArray();
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", sonarRulesText);
        conversationHistory.add(systemMessage);


        List<MatchingResult> results = new ArrayList<>();
        for (String guideline : guidelines) {
            MatchingResult result = getMatchingForGuideline(guideline, conversationHistory);
            results.add(result);
            System.out.println("处理规范: " + guideline + "，匹配分数: " + result.score);
            try {
                Thread.sleep(200); // 延时防止速率限制
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // 将所有结果写入 Excel 文件
        writeResultsToExcel(results, "ComparisonResults0.xlsx");
        System.out.println("比对结果已输出到 ComparisonResults0.xlsx");
    }
}
