package org.example.diablo.sonar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController("/testDeleteOnExit")
public class TestDeleteOnExit {
    @GetMapping("/test")
    public void test(){
        File file = new File("file.txt");
        file.deleteOnExit();  // Noncompliant
    }
}
