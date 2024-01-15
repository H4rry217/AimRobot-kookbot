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
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: AimRobot-QQBot
 * @description:
 * @author: H4rry217
 **/

public class RecentActivePlayerCommand implements CommandListener {

    @Override
    public Message processEvent(CommandHandler commandHandler) {

        if(!commandHandler.getSender().isOwner()) return Text.of("无权执行");

        String name = commandHandler.getParams().get("param");

        AimRobotServerAPI aimRobotServerAPI = RequestUtils.getInstance().getRemoteServer();

        try {
            JsonNode response = RequestUtils.getJsonNode(
                    aimRobotServerAPI.getRecentActivePlayers(SpringUtils.getBean(BotConfig.class).getAccessToken(), new HashMap<>()).execute().body().string());

            StringJoiner stringJoiner = new StringJoiner("\n");
            AtomicInteger i = new AtomicInteger();

            if(name != null){
                name = name.toLowerCase();
                String finalName = name;
                response.get("data").get("result").elements().forEachRemaining((pName) -> {
                    if(pName.asText().toLowerCase().contains(finalName)){
                        stringJoiner.add(pName.asText());
                        i.getAndIncrement();
                    }
                });
            }else{
                response.get("data").get("result").elements().forEachRemaining((pName) -> {
                    stringJoiner.add(pName.asText());
                    i.getAndIncrement();
                });
            }


            if(name != null){
                return Text.of(response.get("msg").asText() + ", 筛选后有 "+i.get()+"个符合条件\n===============\n" + stringJoiner);
            }else{
                return Text.of(response.get("msg").asText() + "\n===============\n" + stringJoiner);
            }

        } catch (IOException e) {
            return Text.of("执行过程中出错");
        }

    }

    @Override
    public String getCommandKeyword() {
        return "rp";
    }

    @Override
    public CommandStyle getCommandStyle() {
        return CommandStyle.Style1;
    }

    @Override
    public boolean isGuildLimit() {
        return true;
    }

}
