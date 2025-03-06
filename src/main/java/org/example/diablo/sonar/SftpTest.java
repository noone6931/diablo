package org.example.diablo.sonar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;

@RestController
@RequestMapping("/sftp")
public class SftpTest {
    @GetMapping("/test1")
    public void test1(){
        SftpUtils sftpUtils = new SftpUtils();
        Cookie cookie = new Cookie("hello", "world");
        sftpUtils.login();
    }

}
