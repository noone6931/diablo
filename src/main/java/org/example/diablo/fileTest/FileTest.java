package org.example.diablo.fileTest;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileTest {
    public static void main(String[] args) throws IOException {
//        File file = new File("测试文件生成时间4");
//        String absolutePath = file.getAbsolutePath();
//        System.out.println("absolutePath:"+absolutePath);
//
//        ExcelWriterBuilder write = EasyExcel.write(file);
//        ExcelWriter build = write.build();
//        WriteSheet build1 = EasyExcel.writerSheet().build();
//
//        build.write(Lists.newArrayList(), build1);

//        file.createNewFile()
//        file.mkdir();
        FileWriter writer = new FileWriter("auto_created_file.txt2");
    }

}
