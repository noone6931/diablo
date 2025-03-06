package org.example.diablo.hougeRedisNo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class HouGeRedisNo {

    static final Logger log = LoggerFactory.getLogger( HouGeRedisNo.class);
    public static void main(String[] args) {

        System.out.println("Hello World!");
        HouGeRedisNo houGeRedisNo = new HouGeRedisNo();
        Date date = new Date();

        String serialNo = houGeRedisNo.getSerialNo(String.valueOf(date.getTime()));
        String serialNo1 = houGeRedisNo.getSerialNo(String.valueOf(date.getTime()));
        System.out.println("serialNo"+serialNo);
        System.out.println("serialNo"+serialNo1);
    }


    public static AtomicLong NUMBER = new AtomicLong();

    public synchronized String getSerialNo(String currentTimestamp){
        if (NUMBER.longValue() != Long.parseLong(currentTimestamp)) {
            NUMBER = new AtomicLong(Long.parseLong(currentTimestamp));
        }
        NUMBER.incrementAndGet();
        return NUMBER.toString();
    }
}
