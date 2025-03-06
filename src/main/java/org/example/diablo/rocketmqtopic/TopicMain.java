package org.example.diablo.rocketmqtopic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TopicMain {
    public static void main(String[] args) {
        // JSON 文件路径
        String filePath = "C:\\Users\\CHENG\\IdeaProjects\\diablo\\src\\main\\resources\\rocketmqTopic.json";

        try {
// 读取 JSON 文件内容到字符串
            String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));

            // 使用 FastJSON 解析 JSON 字符串为 JSONObject
            JSONObject jsonObject = JSON.parseObject(jsonContent);

            // 获取 topicConfigTable 对象
            JSONObject topicConfigTable = jsonObject.getJSONObject("topicConfigTable");

            Set<String> topicSet = new HashSet<>();
            // 遍历 topicConfigTable 的键值对

            Map<String, JSONObject> map = new HashMap<>();
            for (Map.Entry<String, Object> entry : topicConfigTable.entrySet()) {
                String key = entry.getKey();
                JSONObject topicConfig = (JSONObject) entry.getValue();

                // 获取 topicName 属性
                String topicName = topicConfig.getString("topicName");
                map.put(topicName, topicConfig);

                // 输出结果
//                System.out.println( topicName);
//                topicSet.add(topicName);
            }
//            for (String s : topicSet) {
//                System.out.println(s);
//            }
            map.forEach((k,v)->{
                if (k.hashCode() == 0){
                    System.out.println("HASHCODE 为 0 ====== , topicConfig:"+v);
                }
            });
            for (String topic1 : topicSet) {
                if (topic1.hashCode() == 0){
                    System.out.println("HASHCODE 为 0 ====== "+topic1);
                }

//                for (String topic2 : topicSet) {
//                    if (!topic1.equals(topic2)){
//                        int hashCode1 = topic1.hashCode();
//                        int hashCode2 = topic2.hashCode();
//                        if (hashCode2 == 0){
//                            System.out.println("HASHCODE 为 0 ====== "+topic2);
//                        }
////                        System.out.println("hashCode1:"+hashCode1 + ", hashCode2:"+hashCode2 + ", equals result : "+ (hashCode1 == hashCode2) );
//
//                    }
//                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
