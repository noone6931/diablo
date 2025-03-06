package org.example.diablo.thirteenWhy.copy;

import org.springframework.beans.BeanUtils;

public class SpringBeanUtilsCopy {
    public static void copyProperties(Source source, Target target) {
        BeanUtils.copyProperties(source, target); // 浅拷贝
    }

    public static void main(String[] args) {
        // 设置源对象
        Source source = new Source();
        source.setField1("field1");
        source.setField2("field2");

        NestedObject nestedObject = new NestedObject();
        nestedObject.setNestedField("nestedField");
        source.setNestedObject(nestedObject);

        // 目标对象
        Target target = new Target();

        // 进行属性复制
        SpringBeanUtilsCopy.copyProperties(source, target);

        // 修改目标对象的嵌套对象
        target.getNestedObject().setNestedField("modifiedNestedField");

        // 打印源对象和目标对象嵌套对象的字段值
        System.out.println("Source Nested Object Field: " + source.getNestedObject().getNestedField());
        System.out.println("Target Nested Object Field: " + target.getNestedObject().getNestedField());
    }
}
