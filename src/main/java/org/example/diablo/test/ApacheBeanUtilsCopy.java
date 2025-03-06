package org.example.diablo.test;

import org.apache.commons.beanutils.BeanUtils;
import org.example.diablo.thirteenWhy.copy.Source;
import org.example.diablo.thirteenWhy.copy.Target;

public class ApacheBeanUtilsCopy {
    public static void copyProperties(Source source, Target target) {
        try {
            BeanUtils.copyProperties(target, source);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
