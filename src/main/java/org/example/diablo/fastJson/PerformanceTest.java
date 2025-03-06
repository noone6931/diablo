package org.example.diablo.fastJson;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class PerformanceTest {
    public static void main(String[] args) {
        // 创建样例对象
        ComplexObject source = new ComplexObject();
        source.setId(1);
        source.setName("Test");

        // 测试 FastJSON 的性能
        long fastJsonStart = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            // FastJSON 序列化再反序列化
            String jsonString = JSON.toJSONString(source);
            ComplexObject target = JSON.parseObject(jsonString, ComplexObject.class);
        }
        long fastJsonEnd = System.nanoTime();
        System.out.println("FastJSON 耗时: " + (fastJsonEnd - fastJsonStart) + " 纳秒");

        // 测试 BeanUtils 的性能
        long beanUtilsStart = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            ComplexObject target = new ComplexObject();
            // BeanUtils 直接属性拷贝
            BeanUtils.copyProperties(source, target);
        }
        long beanUtilsEnd = System.nanoTime();
        System.out.println("BeanUtils 耗时: " + (beanUtilsEnd - beanUtilsStart) + " 纳秒");
    }

    // 复杂对象类
    static class ComplexObject {
        private int id;
        private String name;
        private List<NestedObject> nestedObjects;

        // Getter 和 Setter
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<NestedObject> getNestedObjects() {
            return nestedObjects;
        }

        public void setNestedObjects(List<NestedObject> nestedObjects) {
            this.nestedObjects = nestedObjects;
        }
    }

    // 嵌套对象类
    static class NestedObject {
        private String description;

        // Getter 和 Setter
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
