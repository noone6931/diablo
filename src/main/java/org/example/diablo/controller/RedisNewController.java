package org.example.diablo.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/redisNew")
public class RedisNewController {

    @Resource
    private StringRedisTemplate clusterRedisTemplate;

    @GetMapping("/checkString")
    public void checkString(){
        int totalKeys = 1000; // 10万条数据
        // 1. 验证小key字符串
        int smallKeyCount = 0;
        for (int i = 0; i < totalKeys / 2; i++) {
            String key = "string:small:" + i;
            if (clusterRedisTemplate.hasKey(key)) {
                smallKeyCount++;
                if (smallKeyCount <= 5) { // 打印前5个示例
                    String value = clusterRedisTemplate.opsForValue().get(key);
                    System.out.println("Small key found: " + key + " = " + value);
                }
            }
        }
        System.out.println("Total small string keys found: " + smallKeyCount + "/500");
    }

    @GetMapping("/checkString2")
    public void checkString2(){
        int totalKeys = 1000; // 10万条数据
        // 1. 验证小key字符串
        int smallKeyCount = 0;
        for (int i = 500; i < totalKeys; i++) {
            String key = "string:small:" + i;
            if (clusterRedisTemplate.hasKey(key)) {
                smallKeyCount++;
                if (smallKeyCount <= 5) { // 打印前5个示例
                    String value = clusterRedisTemplate.opsForValue().get(key);
                    System.out.println("Small key found: " + key + " = " + value);
                }
            }
        }
        System.out.println("Total small string keys found: " + smallKeyCount + "/500");
    }

    @GetMapping("/checkString3")
    public void checkString3(){
        int totalKeys = 1500; // 10万条数据
        // 1. 验证小key字符串
        int smallKeyCount = 0;
        for (int i = 1000; i < totalKeys; i++) {
            String key = "string:small:" + i;
            if (clusterRedisTemplate.hasKey(key)) {
                smallKeyCount++;
                if (smallKeyCount <= 5) { // 打印前5个示例
                    String value = clusterRedisTemplate.opsForValue().get(key);
                    System.out.println("Small key found: " + key + " = " + value);
                }
            }
        }
        System.out.println("Total small string keys found: " + smallKeyCount + "/500");
    }


    @GetMapping("/checkBigValues")
    public void checkBigValues(){
        int bigKeyCount = 0;
        for (int i = 0; i < 10; i++) {
            String key = "string:big:" + i;
            if (clusterRedisTemplate.hasKey(key)) {
                bigKeyCount++;
                String value = clusterRedisTemplate.opsForValue().get(key);
                System.out.println("Big key found: " + key + ", length: " + value.length());
            } else {
                System.out.println("Big key missing: " + key);
            }
        }
        System.out.println("Total big string keys found: " + bigKeyCount + "/10");
    }

    @GetMapping("/checkList")
    public void checkList(){
        int listCount = 0;
        for (int i = 0; i < 1000; i++) {
            String key = "list:" + i;
            if (clusterRedisTemplate.hasKey(key)) {
                listCount++;
                if (listCount <= 5) { // 打印前5个示例
                    List<String> values = clusterRedisTemplate.opsForList().range(key, 0, 9); // 前10个元素
                    System.out.println("List found: " + key + " = " + values);
                }
            }
        }
        System.out.println("Total lists found: " + listCount + "/1000");
    }

    @GetMapping("/checkHash")
    public void checkHash(){
// 4. 验证哈希
        int hashCount = 0;
        for (int i = 0; i < 1000; i++) {
            String key = "hash:" + i;
            if (clusterRedisTemplate.hasKey(key)) {
                hashCount++;
                if (hashCount <= 5) { // 打印前5个示例
                    Map<Object, Object> hash = clusterRedisTemplate.opsForHash().entries(key);
                    System.out.println("Hash found: " + key + " = " + hash);
                }
            }
        }
        System.out.println("Total hashes found: " + hashCount + "/1000");
    }


    @GetMapping("/importString4")
    public void importString4(){
        // 数据量
        int totalKeys = 2000; // 10万条数据
        Random random = new Random();

        // 1. 灌入字符串类型数据（小key）
        for (int i = 1500; i < totalKeys; i++) {
            String key = "string:small:" + i;
            String value = "value-" + random.nextInt(1000);
            clusterRedisTemplate.opsForValue().set(key, value);
        }
    }

}
