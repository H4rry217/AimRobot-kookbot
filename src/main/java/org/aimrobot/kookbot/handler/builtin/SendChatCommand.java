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
 * @program: AimRobot-QQBot
 * @description:
 * @author: H4rry217
 **/

public class SendChatCommand implements CommandListener {

    @Override
    public Message processEvent(CommandHandler commandHandler) {

        if(!commandHandler.getSender().isOwner()) return Text.of("无权执行");

        if(commandHandler.getParams().get("param") != null){

            String content = commandHandler.getParams().get("param");
            String serverId = commandHandler.getParams().get("sid");

            AimRobotServerAPI aimRobotServerAPI = RequestUtils.getInstance().getRemoteServer();

            try {
                JsonNode response = RequestUtils.getJsonNode(
                        aimRobotServerAPI.sendChat(SpringUtils.getBean(BotConfig.class).getAccessToken(), new HashMap<>(){{
                            put("serverId", serverId);
                            put("message", "管理员的大喇叭: "+content);
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
        return "lt";
    }

    @Override
    public CommandStyle getCommandStyle() {
        return CommandStyle.Style1;
    }

    @Override
    public boolean isGuildLimit() {
        return true;
    }

    @Override
    public boolean isRequireAdmin() {
        return true;
    }

}
