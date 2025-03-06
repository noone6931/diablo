package org.example.diablo.jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CompareGuidelinesFromExcel {

    // 请替换为您自己的 API Key 和 API URL
    public static final String API_KEY = "sk-qgvqrmsyafpayelkbgcmcjnndotnxanuwfwxleazbjbiejjf";
    public static final String API_URL = "https://api.siliconflow.cn/v1/chat/completions";
    public static final HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * 用于存储 ChatGPT 返回的比对结果
     */
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
     * 调用 ChatGPT API，对给定的规范（guideline）与全量 Sonar 规则进行比对，
     * 返回 JSON 格式的结果。要求返回的 JSON 格式为：
     * {
     *   "sonarRules": ["rule1", "rule2", ...],
     *   "score": number,
     *   "remark": "说明原因"
     * }
     */
    public static MatchingResult getMatchingSonarRules(String guideline, String sonarRulesText) {
        // 构造 prompt：全量 Sonar 规则 + 当前规范
        String prompt = "下面是所有 Sonar 规则（每条规则一行）：\n"
                + sonarRulesText + "\n\n"
                + "请根据下面的规范，找出最符合该规范的 Sonar 规则（可能有多个），并给出匹配分数（0到5之间，5表示完全匹配）以及说明原因。" +
                "如果没有找到，请根据你所知道的sonar规则，找出一份最符合的sonar规则（因为你会忘记我发你的）。如果实在没有，不要瞎编，直接给0分即可。"
                + "\n规范：\n" + guideline
                + "\n\n请只以严格的 JSON 格式返回结果，格式如下：\n"
                + "{ \"sonarRules\": [\"rule1\", \"rule2\"], \"score\": number, \"remark\": \"...\" }\n不要返回其他任何内容。";

        JSONObject payload = new JSONObject();
        payload.put("model", "Qwen/Qwen2.5-Coder-32B-Instruct");  // 如有需要可调整模型
        JSONArray messages = new JSONArray();
        JSONObject messageObj = new JSONObject();
        messageObj.put("role", "user");
        messageObj.put("content", prompt);
        messages.add(messageObj);
        payload.put("messages", messages);
        payload.put("temperature", 0);
        payload.put("max_tokens", 150);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = JSONObject.parseObject(response.body());
            JSONArray choices = jsonResponse.getJSONArray("choices");
            if (choices != null && choices.size() > 0) {
                JSONObject choice = choices.getJSONObject(0);
                JSONObject message = choice.getJSONObject("message");
                String content = message.getString("content").trim();
                // 解析返回的 JSON 格式字符串
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
                return new MatchingResult(sonarRules, score, remark);
            }
        } catch (Exception e) {
            System.err.println("调用 ChatGPT API 失败，对于规范：" + guideline);
            e.printStackTrace();
        }
        return new MatchingResult(new ArrayList<>(), 0, "");
    }

    public static void main(String[] args) {
        // 假设 Excel 文件为 rules.xlsx，其中：
        // sheet1 存放我们的规范，每行一条，第一列（索引0）为规范文本；
        // sheet2 存放 Sonar 规则，每行一条，第一列（索引0）为 Sonar 规则文本；
        String inputExcel = "C:\\Users\\CHENG\\IdeaProjects\\diablo\\规范.xlsx";
        String outputExcel = "ComparisonResults2.xlsx";

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

        // 遍历每条我们的规范，调用 ChatGPT API
        List<MatchingResult> results = new ArrayList<>();
        for (String guideline : guidelines) {
            MatchingResult result = getMatchingSonarRules(guideline, sonarRulesText);
            results.add(result);
            System.out.println("处理完规范: " + guideline + "，匹配得分: " + result.score);
            // 为避免速率限制，适当延时
            try {
                Thread.sleep(200);
            } catch (InterruptedException ie) {
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

        for (int i = 0; i < guidelines.size(); i++) {
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