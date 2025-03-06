package org.example.diablo.redisson;


import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController("redissonTest")
@RequestMapping("/redissonTest")
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    static Map<String, Integer> lockMap = new HashMap<>();
    static {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            lockMap.put("lock" + i,  random.nextInt(1,10));
        }
    }


    @Resource
    private RedissonClient redissonClient;

    @GetMapping("/lock")
    public void lock(){

        lockMap.forEach((k,v)->{
            RLock lock = redissonClient.getLock(k);
            try {
                lock.tryLock(0,10,TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }


    @GetMapping("/getLock")
    public void getLock(){
        lockMap.forEach((k,v)->{
            RLock lock = redissonClient.getLock(k);
            if (lock.isLocked()){
                logger.info("{} is locked, ttl is :{}", k, lock.remainTimeToLive());
            }else {
                logger.info("{} is  unlock, ttl is :{}",k,  lock.remainTimeToLive());
            }
        });
    }
}
