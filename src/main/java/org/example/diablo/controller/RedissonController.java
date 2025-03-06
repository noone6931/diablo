//package org.example.diablo.controller;
//
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//@RestController
//@RequestMapping("/redisson")
//public class RedissonController {
//
//
//    @Resource
//    private RedissonClient redissonClient;
//
//    @GetMapping("/redisson1")
//    public void redisson1() {
//        RLock lock = redissonClient.getLock("");
////        lock.tryLock(1,1, TimeUnit.MINUTES);
//        lock.lock();
//        try {
//            // do something
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    @GetMapping("/redisson2")
//    public void redisson2() {
//        RLock lock = redissonClient.getLock("");
////        lock.tryLock(1,1, TimeUnit.MINUTES);
//        lock.tryLock();
//        try {
//            // do something
//        } finally {
//            lock.unlock();
//        }
//    }
//
//}
