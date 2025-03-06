//package org.example.diablo.controller;
//
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.Calendar;
//
//@RestController
//public class RedisController {
//
//    @Resource
//    private RedisTemplate<String, String> redisTemplate;
//
//    @GetMapping("/test")
//    public String test() {
//        return "hello world";
//    }
//
//    @GetMapping("/testSetRedis")
//    public String testSetRedis(@RequestParam String key, @RequestParam String value) {
//        redisTemplate.opsForValue().set(key, value);
//        return "success with key: " + key + " and value: " + value;
//    }
//
//    @GetMapping("/testGetRedis")
//    public String testGetRedis(@RequestParam String key) {
//        // 打印key
//        return redisTemplate.opsForValue().get(key);
//    }
//
//    public static void main(String[] args) {
//        int i = Calendar.getInstance().get(Calendar.SECOND);
//        for (int j = 0; j < 2; j++) {
//            System.out.println("当前秒：" + i);
//            System.out.println((i + 60 - j) % 60);
//        }
//    }
//}
