package org.example.diablo.test;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.example.diablo.thirteenWhy.copy.Source;
import org.example.diablo.thirteenWhy.copy.Target;

public class CopyTest {
    public static void main(String[] args) {
        // 创建测试数据
        Source source = new Source();
        source.setField1("field1");
        source.setField2("field2");
        source.setField3("field3");
        source.setField4("field4");
        source.setField5("field5");
        source.setField6(6);
        source.setField7(7.0);
        source.setField8(true);
        source.setField9(9L);
        source.setField10("field10");

        // 测试 Apache BeanUtils
        long apacheStartTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            Target target = new Target();
            ApacheBeanUtilsCopy.copyProperties(source, target);
        }
        long apacheEndTime = System.nanoTime();
        long apacheDuration = apacheEndTime - apacheStartTime;

        System.out.println("Apache BeanUtils 耗时: " + (apacheDuration / 1_000_000.0) + " ms");

        // 测试 Spring BeanUtils
        long springStartTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            Target target = new Target();
            SpringBeanUtilsCopy.copyProperties(source, target);
        }
        long springEndTime = System.nanoTime();
        long springDuration = springEndTime - springStartTime;

        System.out.println("Spring BeanUtils 耗时: " + (springDuration / 1_000_000.0) + " ms");

        // 测试 Apache BeanUtils
        long jsonStartTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            Target target = new Target();
            String jsonString = JSON.toJSONString(source);
            target = JSON.parseObject(jsonString, Target.class);
        }
        long jsonEndTime = System.nanoTime();
        long jsonDuration = jsonEndTime - jsonStartTime;
        System.out.println("Json 耗时: " + (jsonDuration / 1_000_000.0) + " ms");

        // 手动get set
        long getSetStartTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            Target target = new Target();
            target.setField1(source.getField1());
            target.setField2(source.getField2());
            target.setField3(source.getField3());
            target.setField4(source.getField4());
            target.setField5(source.getField5());
            target.setField6(source.getField6());
            target.setField7(source.getField7());
            target.setField8(source.getField8());
            target.setField9(source.getField9());
            target.setField10(source.getField10());
        }
        long getSetEndTime = System.nanoTime();
        long getSetDuration = getSetEndTime - getSetStartTime;
        System.out.println("Get Set 耗时: " + (getSetDuration / 1_000_000.0) + " ms");

    }
}
