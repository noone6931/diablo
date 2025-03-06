package org.example.diablo.fastJson;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;

public class NullPointerTest {
    public static void main(String[] args) {
        Object body = null;

        try {
            // FastJSON 转换
            CrclUploadApplyDto crclUploadApplyDto = JSON.parseObject(JSON.toJSONString(body), CrclUploadApplyDto.class);
            System.out.println("转换成功: " + crclUploadApplyDto);
        } catch (Exception e) {
            System.out.println("捕获到异常: " + e.getMessage());
        }

        // BeanUtils.copyProperties(body, new CrclUploadApplyDto());
        // throw exception
    }

    // 示例目标对象类
    static class CrclUploadApplyDto {
        private String name;

        // Getter 和 Setter
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
