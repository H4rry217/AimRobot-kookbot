package org.aimrobot.kookbot.handler.builtin;

import com.fasterxml.jackson.databind.JsonNode;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.MessagesBuilder;
import love.forte.simbot.message.Text;
import org.aimrobot.kookbot.BotConfig;
import org.aimrobot.kookbot.handler.CommandHandler;
import org.aimrobot.kookbot.handler.CommandListener;
import org.aimrobot.kookbot.utils.AimRobotServerAPI;
import org.aimrobot.kookbot.utils.BotUtils;
import org.aimrobot.kookbot.utils.RequestUtils;
import org.aimrobot.kookbot.utils.SpringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @program: AimRobot-kookbot
 * @description:
 * @author: H4rry217
 **/

public class FindBanCommand implements CommandListener {

    @Override
    public Message processEvent(CommandHandler commandHandler) {
        String serverId = commandHandler.getParams().get("sid");
        String playerName = commandHandler.getParams().get("param") == null? null: (commandHandler.getParams().get("param").isBlank()? null: commandHandler.getParams().get("param"));

        AimRobotServerAPI aimRobotServerAPI = RequestUtils.getInstance().getRemoteServer();

        try {
            JsonNode response = RequestUtils.getJsonNode(
                    aimRobotServerAPI.banHistory(SpringUtils.getBean(BotConfig.class).getAccessToken(), new HashMap<>(){{
                        put("serverId", serverId);
                        put("playerName", playerName);
                    }}).execute().body().string());

            var simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<Object[]> data = new ArrayList<>();

            response.get("data").get("result").elements().forEachRemaining((entry) -> {
                data.add(new Object[]{
                        simpleDateFormat.format(new Date(entry.get("time").asLong())),
                        entry.get("name").asText(),
                        entry.get("reason").asText("-")
                });
            });

            var img = BotUtils.textToResourceImage(
                    BotUtils.prettyShow(
                            new String[]{"时间", "名字", "原因"},
                            data,
                            "==========屏蔽查询=========="
                    )
            );

            return new MessagesBuilder()
                    .text(response.get("msg").asText())
                    .append(img)
                    .build();

        } catch (IOException e) {
            return Text.of("执行过程中出错");
        }
    }

    @Override
    public String getCommandKeyword() {
        return "pb";
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
