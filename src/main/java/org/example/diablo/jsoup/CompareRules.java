package org.example.diablo.jsoup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于比对两个 sheet 中的规范规则，
 * 对每个 sheet1 的规范，遍历 sheet2 中所有 sonar 规则，
 * 调用 ChatGPT API 比较相似度，返回 0~5 的匹配分数，
 * 最后输出匹配结果到新的 Excel 文件。
 */
public class CompareRules {

    // 请替换成您自己的 OpenAI API Key
    public static final String API_KEY = "sk-qgvqrmsyafpayelkbgcmcjnndotnxanuwfwxleazbjbiejjf";

    public static final String API_URL = "https://api.siliconflow.cn/v1/chat/completions";
    // 采用 Java 11 HttpClient 进行 HTTP 请求
    public static final HttpClient httpClient = HttpClient.newHttpClient();


    /**
     * 用于存储调用 API 返回的相似度结果。
     */
    public static class SimilarityResult {
        double score;
        String remark;

        public SimilarityResult(double score, String remark) {
            this.score = score;
            this.remark = remark;
        }
    }

    /**
     * 用于保存单条比对结果。
     * ruleOur 为 sheet1 中的规范，entries 保存所有得分最高的 sonar 规则
     */
    public static class ComparisonResult {
        String ruleOur;
        List<SimilarEntry> similarEntries = new ArrayList<>();

        public ComparisonResult(String ruleOur) {
            this.ruleOur = ruleOur;
        }
    }

    /**
     * 保存 sonar 规则、匹配分数及备注。
     */
    public static class SimilarEntry {
        String sonarRule;
        double score;
        String remark;

        public SimilarEntry(String sonarRule, double score, String remark) {
            this.sonarRule = sonarRule;
            this.score = score;
            this.remark = remark;
        }
    }

    public static void main(String[] args) {
        // Excel 文件路径（请根据实际情况调整）
        String inputExcel = "C:\\Users\\CHENG\\IdeaProjects\\diablo\\规范.xlsx";
        String outputExcel = "ComparisonResults.xlsx";
        try (Workbook workbook = WorkbookFactory.create(new File(inputExcel))) {
            // 假设第一个 sheet 为我们自己的规范，第二个 sheet 为 sonar 规则（均在第一列）
            Sheet sheetOur = workbook.getSheetAt(1);
            Sheet sheetSonar = workbook.getSheetAt(2);

            List<ComparisonResult> results = new ArrayList<>();

            // 遍历 sheet1 中的每一条规范（假设规范存放在第一列）
            for (int i = 0; i <= sheetOur.getLastRowNum(); i++) {
                Row rowOur = sheetOur.getRow(i);
                if (rowOur == null) continue;
                Cell cellOur = rowOur.getCell(0);
                if (cellOur == null) continue;
                String ruleOur = cellOur.getStringCellValue().trim();
                if (ruleOur.isEmpty()) continue;

                ComparisonResult compResult = new ComparisonResult(ruleOur);
                double bestScore = -1;
                // 遍历 sheet2 中的所有 sonar 规则
                for (int j = 0; j <= sheetSonar.getLastRowNum(); j++) {
                    Row rowSonar = sheetSonar.getRow(j);
                    if (rowSonar == null) continue;
                    Cell cellSonar = rowSonar.getCell(0);
                    if (cellSonar == null) continue;
                    String ruleSonar = cellSonar.getStringCellValue().trim();
                    if (ruleSonar.isEmpty()) continue;

                    // 调用 API 比对两个规则，获取匹配分数和备注
                    SimilarityResult simResult = getSimilarityResult(ruleOur, ruleSonar);
                    double score = simResult.score;
                    // 若找到更高分，则清空之前记录，并保存当前 sonar 规则
                    if (score > bestScore) {
                        bestScore = score;
                        compResult.similarEntries.clear();
                        compResult.similarEntries.add(new SimilarEntry(ruleSonar, score, simResult.remark));
                    } else if (score == bestScore) {
                        // 若分数相同，则追加到列表中
                        compResult.similarEntries.add(new SimilarEntry(ruleSonar, score, simResult.remark));
                    }
                }
                results.add(compResult);
                System.out.println("比对完成一条规范，最高得分：" + bestScore);
            }

            // 输出结果到新的 Excel 文件
            Workbook outputWorkbook = new XSSFWorkbook();
            Sheet outputSheet = outputWorkbook.createSheet("Comparison");
            int rowIndex = 0;
            // 写入表头
            Row header = outputSheet.createRow(rowIndex++);
            header.createCell(0).setCellValue("我们的规范");
            header.createCell(1).setCellValue("Sonar规则");
            header.createCell(2).setCellValue("匹配分数(0~5)");
            header.createCell(3).setCellValue("备注");

            // 写入比对结果
            for (ComparisonResult comp : results) {
                boolean firstEntry = true;
                for (SimilarEntry entry : comp.similarEntries) {
                    Row row = outputSheet.createRow(rowIndex++);
                    if (firstEntry) {
                        row.createCell(0).setCellValue(comp.ruleOur);
                        firstEntry = false;
                    }
                    row.createCell(1).setCellValue(entry.sonarRule);
                    row.createCell(2).setCellValue(entry.score);
                    row.createCell(3).setCellValue(entry.remark);
                }
            }
            // 自动调整各列宽度
            for (int i = 0; i < 4; i++) {
                outputSheet.autoSizeColumn(i);
            }
            try (FileOutputStream fos = new FileOutputStream(outputExcel)) {
                outputWorkbook.write(fos);
            }
            outputWorkbook.close();
            System.out.println("比对结果已生成至文件：" + outputExcel);
        } catch (Exception e) {
            System.err.println("处理过程中出现异常：");
            e.printStackTrace();
        }
    }


    /**
     * 调用 ChatGPT API 对两个规则进行比对，返回 JSON 格式的结果，其中包含：
     * - score：0~5 的匹配分数
     * - remark：给出分数的原因说明
     * <p>
     * 请求示例：
     * “请比较以下两个代码规范规则的相似度，并返回一个JSON格式的字符串，要求包含两个字段：score和remark。
     * 其中score字段的值应为一个0到5之间的数字，0表示完全不匹配，5表示完全匹配；
     * remark字段为字符串，说明为什么给出该分数。请只返回JSON格式的结果，不要附加其他文字。
     * 规范A: [rule1]
     * 规范B: [rule2]”
     *
     * @param rule1 我们自己的规范
     * @param rule2 sonar 的规则
     * @return 相似度结果（调用失败则返回 score=0, remark=""）
     */
    public static SimilarityResult getSimilarityResult(String rule1, String rule2) {
        String prompt = "请比较以下两个代码规范规则的相似度，并返回一个JSON格式的字符串，要求包含两个字段：\"score\"和\"remark\"。"
                + "其中score字段的值应为一个0到5之间的数字，0表示完全不匹配，5表示完全匹配；"
                + "remark字段为字符串，说明为什么给出该分数。请只返回JSON格式的结果，不要附加其他文字。\n"
                + "规范A: " + rule1 + "\n"
                + "规范B: " + rule2;

        // 构造请求 JSON（参考 ChatGPT API 文档）
        JSONObject payload = new JSONObject();
        payload.put("model", "Qwen/Qwen2.5-Coder-32B-Instruct");
        JSONArray messages = new JSONArray();
        JSONObject messageObj = new JSONObject();
        messageObj.put("role", "user");
        messageObj.put("content", prompt);
        messages.add(messageObj);
        payload.put("messages", messages);
        payload.put("temperature", 0);
        payload.put("max_tokens", 100);  // 备注说明可能需要更多 token

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("调用 ChatGPT API 返回，结果：" + response.body());
            JSONObject jsonResponse = JSONObject.parseObject(response.body());
            JSONArray choices = jsonResponse.getJSONArray("choices");
            if (choices != null && !choices.isEmpty()) {
                JSONObject choice = choices.getJSONObject(0);
                JSONObject message = choice.getJSONObject("message");
                String content = message.getString("content").trim();
                // 解析返回的 JSON 格式字符串
                JSONObject resultObj = JSONObject.parseObject(content);
                double score = resultObj.getDoubleValue("score");
                String remark = resultObj.getString("remark");
                return new SimilarityResult(score, remark);
            }
        } catch (Exception e) {
            System.err.println("调用 ChatGPT API 失败，规则比对返回 0分，备注为空。");
            e.printStackTrace();
        }
        return new SimilarityResult(0, "");
    }
}