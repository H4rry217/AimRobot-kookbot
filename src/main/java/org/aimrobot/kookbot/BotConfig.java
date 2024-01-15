package org.aimrobot.kookbot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: AimRobot-kookbot
 * @description:
 * @author: H4rry217
 **/

@EnableConfigurationProperties
@Configuration
@Getter
@Setter
public class BotConfig {

    @Value("${remote-server.url}")
    private String apiUrl;

    @Value("${remote-server.token}")
    private String accessToken;

    @Value("${robot.command-prefix}")
    private String commandPrefix;

    @Value("${robot.guild-id-limit}")
    private String guildIdLimit;

}
