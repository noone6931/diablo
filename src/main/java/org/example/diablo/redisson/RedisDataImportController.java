//package org.example.diablo.redisson;
//
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.Random;
//
//@RestController
//@RequestMapping("/redisDataImport")
//public class RedisDataImportController {
//
//    @Resource
//    private StringRedisTemplate stringRedisTemplate;
//
//    @GetMapping("/importString")
//    public void importString() {
//        // 数据量
//        int totalKeys = 100; // 10万条数据
//        Random random = new Random();
//        // 1. 灌入字符串类型数据（小key）
//        for (int i = 0; i < totalKeys / 2; i++) {
//            String key = "string:small:" + i;
//            String value = "value-" + random.nextInt(1000);
//            stringRedisTemplate.opsForValue().set(key, value);
//            if (i % 10000 == 0) {
//                System.out.println("Inserted " + i + " small string keys");
//            }
//        }
//    }
//
//    public void importBigValues() {
//
//    }
//
//    public void importList() {
//
//    }
//
//    public void importHash() {
//
//    }
//
//    // 生成指定大小的随机字符串
//    private String generateBigValue(int sizeInBytes) {
//        StringBuilder sb = new StringBuilder(sizeInBytes);
//        Random random = new Random();
//        for (int i = 0; i < sizeInBytes; i++) {
//            sb.append((char) ('a' + random.nextInt(26)));
//        }
//        return sb.toString();
//    }
//}
