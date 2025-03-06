package org.example.diablo.fastJson;

import com.alibaba.fastjson.JSON;

public class TypeSafetyTest {
    public static void main(String[] args) {
        // 创建样例对象
        SourceObject source = new SourceObject();
        source.setId(1);
        source.setName("Test");
        source.setExtraField("Extra");

        // FastJSON 转换
        String jsonString = JSON.toJSONString(source);
        TargetObject target = JSON.parseObject(jsonString, TargetObject.class);

        // 打印结果
        System.out.println("Target Object ID: " + target.getId());
        System.out.println("Target Object Name: " + target.getName());
    }

    // 额外字段的源对象类
    static class SourceObject {
        private int id;
        private String name;
        private String extraField; // 目标对象没有这个字段

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

        public String getExtraField() {
            return extraField;
        }

        public void setExtraField(String extraField) {
            this.extraField = extraField;
        }
    }

    // 目标对象类（没有 extraField 字段）
    static class TargetObject {
        private int id;
        private String name;

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
    }
}
