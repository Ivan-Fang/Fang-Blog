package com.ivanfang.fangblog;

import com.ivanfang.fangblog.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ThreadLocalTest {

    @Test
    public void test01() {
        ThreadLocal tl = new ThreadLocal<>();
        User user = new User();

        new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                tl.set("天空");
                user.setId(1);
                System.out.println(Thread.currentThread().getName() + " : " + tl.get() + " : " + user.getId());
            }
        }, "Blue").start();

        new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                tl.set("綠地");
                user.setId(2);
                Thread.yield();
                System.out.println(Thread.currentThread().getName() + " : " + tl.get() + " : " + user.getId());
            }
        }, "Green").start();
    }

}
