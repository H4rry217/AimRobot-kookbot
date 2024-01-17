package org.aimrobot.kookbot;

import love.forte.simboot.spring.autoconfigure.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSimbot
public class AimRobotKookbotApplication {

    public static void main(String[] args) {
        new Thread(() -> {
            SpringApplication.run(AimRobotKookbotApplication.class, args);
        }).start();
    }

}
