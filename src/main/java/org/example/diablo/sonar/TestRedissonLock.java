package org.example.diablo.sonar;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestRedissonLock {

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private TestService testService;

    @GetMapping("/test1")
    public void test1(){
        RLock lock = redissonClient.getLock("test1:lock");
        lock.lock();
        try {
            System.out.println("test1");
        }finally {
//            lock.unlock();
        }
    }

    @GetMapping("/test2")
    public void test2(){
        RLock lock = redissonClient.getLock("test1:lock");
        lock.lock();
        try {
            System.out.println("test1");
            testService.test(lock);
        }finally {
//            lock.unlock();
        }
    }
}
