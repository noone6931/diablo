package org.example.diablo.controller;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.example.diablo.util.HaoDate;
import org.example.diablo.util.HaoDateSchema;
import org.example.diablo.util.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
public class UploadController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/uploadFile")
    public void uploadFile(@RequestPart MultipartFile file) {
        logger.info("file.size:{}", file.getSize());
    }

//    public static void main(String[] args) throws InterruptedException {
//        HaoDate haoDate = new HaoDate();
//        Schema<HaoDate> schema = new HaoDateSchema();
//        LinkedBuffer buffer = LinkedBuffer.allocate(512);
//
//
//        // 序列化
//        byte[] serializedData = ProtostuffIOUtil.toByteArray(haoDate, schema, buffer);
//
//        // 模拟延时
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//
//        // 反序列化
//        HaoDate deserializedDate = schema.newMessage();
//        ProtostuffIOUtil.mergeFrom(serializedData, deserializedDate, schema);
//
//        System.out.println("Original Date: " + haoDate);
//        System.out.println("Deserialized Date: " + deserializedDate);
//    }

    public static void main(String[] args) {

    }

}
