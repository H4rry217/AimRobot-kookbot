package org.aimrobot.kookbot.handler.builtin;

import com.fasterxml.jackson.databind.JsonNode;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.MessagesBuilder;
import love.forte.simbot.message.Text;
import love.forte.simbot.resources.ByteArrayResource;
import org.aimrobot.kookbot.BotConfig;
import org.aimrobot.kookbot.handler.CommandHandler;
import org.aimrobot.kookbot.handler.CommandListener;
import org.aimrobot.kookbot.utils.AimRobotServerAPI;
import org.aimrobot.kookbot.utils.RequestUtils;
import org.aimrobot.kookbot.utils.SpringUtils;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

/**
 * @program: AimRobot-kookbot
 * @description:
 * @author: H4rry217
 **/

public class ScreenshotCommand implements CommandListener {

    @Override
    public Message processEvent(CommandHandler commandHandler) {
        String serverId = commandHandler.getParams().containsKey("sid")? commandHandler.getParams().get("sid"): commandHandler.getParams().get("param");

        AimRobotServerAPI aimRobotServerAPI = RequestUtils.getInstance().getRemoteServer();

        try {
            JsonNode response = RequestUtils.getJsonNode(
                    aimRobotServerAPI.getScreenshot(SpringUtils.getBean(BotConfig.class).getAccessToken(), new HashMap<>(){{
                        put("serverId", serverId);
                    }}).execute().body().string());


            String base64String = response.get("data").get("result").asText();
            byte[] imageBytes = Base64.getDecoder().decode(base64String);

            return new MessagesBuilder()
                    .text(response.get("msg").asText())
                    .append(love.forte.simbot.message.Image.of(new ByteArrayResource(UUID.randomUUID().toString(), imageBytes)))
                    .build();

        } catch (IOException e) {
            return Text.of("执行过程中出错" + e);
        }

    }

    @Override
    public String getCommandKeyword() {
        return "jt";
    }

    @Override
    public CommandStyle getCommandStyle() {
        return CommandStyle.Style1;
    }

    @Override
    public boolean isRequireAdmin() {
        return true;
    }

    @Override
    public boolean isGuildLimit() {
        return true;
    }
}
