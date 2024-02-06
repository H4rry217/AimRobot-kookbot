package org.aimrobot.kookbot.handler.builtin;

import com.fasterxml.jackson.databind.JsonNode;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.Text;
import okhttp3.ResponseBody;
import org.aimrobot.kookbot.handler.CommandHandler;
import org.aimrobot.kookbot.handler.CommandListener;
import org.aimrobot.kookbot.utils.AimRobotServerAPI;
import org.aimrobot.kookbot.utils.BotUtils;
import org.aimrobot.kookbot.utils.RequestUtils;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: AimRobot-QQBot
 * @description:
 * @author: H4rry217
 **/

public class ServerCommand implements CommandListener {

    @Override
    public Message processEvent(CommandHandler commandHandler) {

        AimRobotServerAPI api = RequestUtils.getInstance().getRemoteServer();
        Call<ResponseBody> call = api.getServerInfos();

        try {
            Response<ResponseBody> execute = call.execute();
            JsonNode jsonNode = RequestUtils.getJsonNode(execute.body().string());

            List<Object[]> data = new ArrayList<>();
            jsonNode.get("data").get("result").fields().forEachRemaining((entry) -> {
                var serverData = entry.getValue();
                data.add(new Object[]{
                        entry.getKey(),
                        serverData.get("alive").asBoolean(),
                        serverData.has("runTask")? serverData.get("runTask").asText(): "-",
                        serverData.has("state")? serverData.get("state"): "-",
                        serverData.has("errorCount")? serverData.get("errorCount").asInt(): "-",
                        serverData.has("timestamp")? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(serverData.get("timestamp").asLong())): "-"
                });
            });

            return BotUtils.textToResourceImage(
                    BotUtils.prettyShow(
                            new String[]{"ServerID", "Alive", "Task", "State", "ErrorCount", "LastActive"},
                            data,
                            "==========当前已连接ARL=========="
                    )
            );

        } catch (IOException e) {
            return Text.of("查询过程中出错");
        }

    }

    @Override
    public String getCommandKeyword() {
        return "server";
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
