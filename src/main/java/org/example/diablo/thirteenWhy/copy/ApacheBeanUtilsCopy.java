package org.example.diablo.thirteenWhy.copy;

import org.apache.commons.beanutils.BeanUtils;

public class ApacheBeanUtilsCopy {
    public static void copyProperties(Source source, Target target) {
        try {
            BeanUtils.copyProperties(target, source); // 浅拷贝
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        ApacheBeanUtilsCopy.copyProperties(source, target);

        // 修改目标对象的嵌套对象
        target.getNestedObject().setNestedField("modifiedNestedField");

        // 打印源对象和目标对象嵌套对象的字段值
        System.out.println("Source Nested Object Field: " + source.getNestedObject().getNestedField());
        System.out.println("Target Nested Object Field: " + target.getNestedObject().getNestedField());
    }
}
