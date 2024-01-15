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
import java.util.ArrayList;
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
        Call<ResponseBody> call = api.getServerIds();

        try {
            Response<ResponseBody> execute = call.execute();
            JsonNode jsonNode = RequestUtils.getJsonNode(execute.body().string());

            List<Object[]> data = new ArrayList<>();
            jsonNode.get("data").get("result").elements().forEachRemaining((node) -> {
                data.add(new Object[]{
                        node.asText()
                });
            });

            return BotUtils.textToResourceImage(
                    BotUtils.prettyShow(
                            new String[]{"ServerID"},
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
