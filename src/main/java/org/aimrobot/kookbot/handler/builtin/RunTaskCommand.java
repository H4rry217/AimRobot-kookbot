package org.aimrobot.kookbot.handler.builtin;

import com.fasterxml.jackson.databind.JsonNode;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.Text;
import org.aimrobot.kookbot.BotConfig;
import org.aimrobot.kookbot.handler.CommandHandler;
import org.aimrobot.kookbot.handler.CommandListener;
import org.aimrobot.kookbot.utils.AimRobotServerAPI;
import org.aimrobot.kookbot.utils.RequestUtils;
import org.aimrobot.kookbot.utils.SpringUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * @program: AimRobot-kookbot
 * @description:
 * @author: H4rry217
 **/

public class RunTaskCommand implements CommandListener {

    @Override
    public Message processEvent(CommandHandler commandHandler) {
        if(commandHandler.getParams().get("param") != null) {

            String serverId = commandHandler.getParams().get("sid");
            String task = commandHandler.getParams().get("param");

            AimRobotServerAPI aimRobotServerAPI = RequestUtils.getInstance().getRemoteServer();

            try {
                JsonNode response = RequestUtils.getJsonNode(
                        aimRobotServerAPI.runTask(SpringUtils.getBean(BotConfig.class).getAccessToken(), new HashMap<>(){{
                            put("serverId", serverId);
                            put("task", task);
                        }}).execute().body().string());

                return Text.of(response.get("msg").asText());

            } catch (IOException e) {
                return Text.of("执行过程中出错");
            }

        }

        return Text.of("参数缺失");
    }

    @Override
    public String getCommandKeyword() {
        return "task";
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
