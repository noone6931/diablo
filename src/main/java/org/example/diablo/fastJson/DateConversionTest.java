package org.example.diablo.fastJson;

import com.alibaba.fastjson.JSON;

import java.util.Date;

public class DateConversionTest {
    public static void main(String[] args) {
        // 创建包含日期的对象
        DateObject source = new DateObject();
        source.setDate(new Date());

        // FastJSON 转换
        String jsonString = JSON.toJSONString(source);
        DateObject target = JSON.parseObject(jsonString, DateObject.class);

        // 打印结果
        System.out.println("原始日期: " + source.getDate());
        System.out.println("转换后的日期: " + target.getDate());
    }

    // 示例包含日期的对象类
    static class DateObject {
        private Date date;

        // Getter 和 Setter
        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }
}
