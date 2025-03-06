package org.example.diablo.test;

import org.example.diablo.thirteenWhy.copy.Source;
import org.example.diablo.thirteenWhy.copy.Target;
import org.springframework.beans.BeanUtils;

public class SpringBeanUtilsCopy {
    public static void copyProperties(Source source, Target target) {
        BeanUtils.copyProperties(source, target);
    }
}
