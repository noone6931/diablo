package org.example.diablo.sonar;

import org.redisson.api.RLock;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public void test(RLock lock) {
        lock.unlock();
    }
}
