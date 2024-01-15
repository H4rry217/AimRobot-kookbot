package org.aimrobot.kookbot.handler.builtin;

import com.fasterxml.jackson.databind.JsonNode;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.Text;
import org.aimrobot.kookbot.handler.CommandHandler;
import org.aimrobot.kookbot.handler.CommandListener;
import org.aimrobot.kookbot.utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: AimRobot-QQBot
 * @description:
 * @author: H4rry217
 **/

public class QueryServerCommand implements CommandListener {

    @Override
    public Message processEvent(CommandHandler commandHandler) {
        if(commandHandler.getParams().get("param") != null){
            String serverName = commandHandler.getParams().get("param");

            GametoolAPI gametoolAPI = RequestUtils.getInstance().getGametoolAPI();

            try {
                JsonNode serverData = RequestUtils.getJsonNode(gametoolAPI.queryServers(serverName).execute().body().string());

                if(serverData.has("errors")){
                    return Text.of(serverData.get("errors").get(0).asText());
                }


                List<Object[]> data = new ArrayList<>();
                serverData.get("servers").elements().forEachRemaining((server) -> {
                    data.add(new Object[]{
                            server.get("prefix").asText(),
                            String.format("%d/%d[%d]", server.get("playerAmount").asInt(), server.get("maxPlayers").asInt(), server.get("inQue").asInt()),
                            server.get("inSpectator").asInt(),
                            server.get("region").asText() + "/" + server.get("country").asText(),
                            server.get("mode").asText() + "/" + server.get("currentMap").asText(),
                            server.get("gameId").asText(),
                            server.get("ownerId").asText(),
                    });
                });

                return BotUtils.textToResourceImage(
                        BotUtils.prettyShow(
                                new String[]{"名词", "人数", "观战", "地区", "地图", "GameId", "OwnerId"},
                                data,
                                "==========查询到 "+data.size()+" 个服务器=========="
                        )
                );

                /**build image output**/

            } catch (IOException e) {
                return Text.of("查询数据过程中出错");
            }

        }

        return Text.of("请输入服务器名称");
    }

    @Override
    public String getCommandKeyword() {
        return "fwq";
    }

    @Override
    public CommandStyle getCommandStyle() {
        return CommandStyle.Style1;
    }


}
