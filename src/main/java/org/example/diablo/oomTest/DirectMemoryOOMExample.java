package org.example.diablo.oomTest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/oom")
public class DirectMemoryOOMExample {


    @GetMapping("/test1")
    public void test() {
        List<ByteBuffer> buffers = new ArrayList<>();
        while (true) {
            // 分配 Direct ByteBuffer（直接内存）
            buffers.add(ByteBuffer.allocateDirect(1024 * 1024)); // 1MB
        }
    }

}
