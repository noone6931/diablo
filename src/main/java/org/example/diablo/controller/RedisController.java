package org.example.diablo.controller;


import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private StringRedisTemplate sentinelRedisTemplate;


    @GetMapping("/importString")
    public void importString(){
        // 数据量
        int totalKeys = 1000; // 10万条数据
        Random random = new Random();

        // 1. 灌入字符串类型数据（小key）
        for (int i = 0; i < totalKeys / 2; i++) {
            String key = "string:small:" + i;
            String value = "value-" + random.nextInt(1000);
            sentinelRedisTemplate.opsForValue().set(key, value);
            if (i % totalKeys == 0) {
                System.out.println("Inserted " + i + " small string keys");
            }
        }
    }

    @GetMapping("/importString2")
    public void importString2(){
        // 数据量
        int totalKeys = 1000; // 10万条数据
        Random random = new Random();

        // 1. 灌入字符串类型数据（小key）
        for (int i = 500; i < totalKeys; i++) {
            String key = "string:small:" + i;
            String value = "value-" + random.nextInt(1000);
            sentinelRedisTemplate.opsForValue().set(key, value);
        }
    }

    @GetMapping("/importString3")
    public void importString3(){
        // 数据量
        int totalKeys = 1500; // 10万条数据
        Random random = new Random();

        // 1. 灌入字符串类型数据（小key）
        for (int i = 1000; i < totalKeys; i++) {
            String key = "string:small:" + i;
            String value = "value-" + random.nextInt(1000);
            sentinelRedisTemplate.opsForValue().set(key, value);
        }
    }


    @GetMapping("/importHash")
    public void importHash(){
        Random random = new Random();
        // 4. 灌入哈希数据
        for (int i = 0; i < 1000; i++) {
            String key = "hash:" + i;
            Map<String, String> hash = new HashMap<>();
            for (int j = 0; j < 50; j++) {
                hash.put("field" + j, "value-" + random.nextInt(1000));
            }
            sentinelRedisTemplate.opsForHash().putAll(key, hash);
            if (i % 100 == 0) {
                System.out.println("Inserted " + i + " hashes");
            }
        }
    }

    @GetMapping("/importList")
    public void importList(){
        Random random = new Random();
        // 3. 灌入列表数据
        for (int i = 0; i < 1000; i++) {
            String key = "list:" + i;
            List<String> values = new ArrayList<>();
            for (int j = 0; j < 100; j++) {
                values.add("item-" + random.nextInt(1000));
            }
            sentinelRedisTemplate.opsForList().rightPushAll(key, values);
            if (i % 100 == 0) {
                System.out.println("Inserted " + i + " lists");
            }
        }
    }
    @GetMapping("/importBigValues")
    public void importBigValues(){
        // 2. 灌入大key数据（5MB）
        String bigValue = generateBigValue(5 * 1024 * 1024); // 5MB
        for (int i = 0; i < 10; i++) {
            String key = "string:big:" + i;
            sentinelRedisTemplate.opsForValue().set(key, bigValue);
            System.out.println("Inserted big key: " + key);
        }
    }
    // 生成指定大小的随机字符串
    private String generateBigValue(int sizeInBytes) {
        StringBuilder sb = new StringBuilder(sizeInBytes);
        Random random = new Random();
        for (int i = 0; i < sizeInBytes; i++) {
            sb.append((char) ('a' + random.nextInt(26)));
        }
        return sb.toString();
    }


    @GetMapping("/checkString4")
    public void checkString4(){
        int totalKeys = 2000; // 10万条数据
        // 1. 验证小key字符串
        int smallKeyCount = 0;
        for (int i = 1500; i < totalKeys; i++) {
            String key = "string:small:" + i;
            if (sentinelRedisTemplate.hasKey(key)) {
                smallKeyCount++;
                if (smallKeyCount <= 5) { // 打印前5个示例
                    String value = sentinelRedisTemplate.opsForValue().get(key);
                    System.out.println("Small key found: " + key + " = " + value);
                }
            }
        }
        System.out.println("Total small string keys found: " + smallKeyCount + "/500");
    }

}
