package org.aimrobot.kookbot.handler.builtin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.Text;
import org.aimrobot.kookbot.handler.CommandHandler;
import org.aimrobot.kookbot.handler.CommandListener;
import org.aimrobot.kookbot.utils.BfvCommunityAPI;
import org.aimrobot.kookbot.utils.BotUtils;
import org.aimrobot.kookbot.utils.Constant;
import org.aimrobot.kookbot.utils.GametoolAPI;
import org.aimrobot.kookbot.utils.RequestUtils;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * @program: AimRobot-QQBot
 * @description:
 * @author: H4rry217
 **/

public class QueryPlayerCommand implements CommandListener {

    @Override
    public Message processEvent(CommandHandler commandHandler) {
        if(commandHandler.getParams().get("param") != null){
            long startTime = System.currentTimeMillis();

            String playerName = commandHandler.getParams().get("param");

            GametoolAPI gametoolAPI = RequestUtils.getInstance().getGametoolAPI();
            BfvCommunityAPI bfvCommunityAPI = RequestUtils.getInstance().getBfvCommunityAPI();
            StringBuilder outputBuilder = new StringBuilder();

            Long playerId;

            try {
                JsonNode statData = RequestUtils.getJsonNode(gametoolAPI.getPlayerData(playerName, "zh-cn").execute().body().string());

                if(statData.has("errors")){
                    return Text.of(statData.get("errors").get(0).asText());
                }

                playerId = statData.get("id").asLong();

                /**build image output**/
                outputBuilder.append(String.format("玩家[%s]的信息: ",
                        statData.get("userName").asText()
                )).append("\n");

                outputBuilder.append(String.format("等级：%d，SPM：%.2f，KPM：%.2f，KD：%.2f",
                        statData.get("rank").asInt(),
                        statData.get("scorePerMinute").asDouble(),
                        statData.get("killsPerMinute").asDouble(),
                        statData.get("killDeath").asDouble()
                )).append("\n");

                outputBuilder.append(String.format("击杀：%d，死亡：%d，救人数：%d，游戏时间：%d小时",
                        statData.get("kills").asInt(),
                        statData.get("deaths").asInt(),
                        statData.get("revives").asInt(),
                        statData.get("secondsPlayed").asInt() / 3600
                )).append("\n");

                outputBuilder.append("武器数据如下(按 击杀/KPM/爆头率/命中率/击杀效率 排序)：").append("\n");

                List<JsonNode> weapons = new ObjectMapper().convertValue(statData.get("weapons"), new TypeReference<List<JsonNode>>(){});
                weapons.sort(new WeaponComparator());
                for (int i = 0; i < 5; i++) {

                    JsonNode weaponNode = weapons.get(weapons.size() - 1 - i);
                    int fullCharCount = BotUtils.countFullWidthChar(weaponNode.get("weaponName").asText());
                    String dynamicPattern = "  %-"+ (25-fullCharCount) +"s   %-6d   %-6.2f   %-6s   %-6s   %-6.2f";

                    outputBuilder.append(String.format(dynamicPattern,
                            weaponNode.get("weaponName").asText(),
                            weaponNode.get("kills").asInt(),
                            weaponNode.get("killsPerMinute").asDouble(),
                            weaponNode.get("headshots").asText(),
                            weaponNode.get("accuracy").asText(),
                            weaponNode.get("hitVKills").asDouble()
                    )).append("\n");
                }

                outputBuilder.append("载具数据如下(按 击杀/KPM/摧毁数 排序)：").append("\n");

                List<JsonNode> vehicles = new ObjectMapper().convertValue(statData.get("vehicles"), new TypeReference<List<JsonNode>>(){});
                vehicles.sort(new VehicleComparator());
                for (int i = 0; i < 5; i++) {

                    JsonNode vehicleNode = vehicles.get(vehicles.size() - 1 - i);
                    int fullCharCount = BotUtils.countFullWidthChar(vehicleNode.get("vehicleName").asText());
                    String dynamicPattern = "  %-"+ (25-fullCharCount) +"s   %-6d   %-6.2f   %-6d";

                    outputBuilder.append(String.format(dynamicPattern,
                            vehicleNode.get("vehicleName").asText(),
                            vehicleNode.get("kills").asInt(),
                            vehicleNode.get("killsPerMinute").asDouble(),
                            vehicleNode.get("destroyed").asInt()
                    )).append("\n");
                }

                /**build image output**/

            } catch (IOException e) {
                return Text.of("查询数据过程中出错");
            }

            String patternBfban = "联Ban查询结果：%s";
            try {
                JsonNode bfbanData = RequestUtils.getJsonNode(gametoolAPI.checkban(playerId).execute().body().string());
                JsonNode node = bfbanData.get("personaids").get(playerId.toString());
                patternBfban = String.format(
                        patternBfban,
                        node.has("status")? Constant.BFBAN_STATUS.get(node.get("status").asInt()) : "无记录"
                );
            }catch (IOException e) {
                patternBfban = String.format(patternBfban, "获取失败");
            }finally {
                outputBuilder.append(patternBfban).append("\n");
            }

            String patternRobot = "机器人服社区数据库情况：%s";
            try {
                JsonNode communityData = RequestUtils.getJsonNode(bfvCommunityAPI.findPlayerStatus(playerId).execute().body().string());
                patternRobot = String.format(
                        patternRobot,
                        communityData.get("status").asInt() == 1? Constant.BFV_COMMUNITY_STATUS.get(Integer.parseInt(communityData.get("data").get(0).asText())): "获取失败"
                );
            }catch (IOException e) {
                patternRobot = String.format(patternRobot, "获取失败");
            }finally {
                outputBuilder.append(patternRobot).append("\n");
            }

            outputBuilder.append(String.format("本次查询用时：%.1f秒", ((System.currentTimeMillis() - startTime) / 1000f)));

            return BotUtils.textToResourceImage(outputBuilder.toString());

        }

        return Text.of("请输入玩家名称");
    }

    @Override
    public String getCommandKeyword() {
        return "cx";
    }

    @Override
    public CommandStyle getCommandStyle() {
        return CommandStyle.Style1;
    }

    private static final class WeaponComparator implements Comparator<JsonNode> {

        @Override
        public int compare(JsonNode o1, JsonNode o2) {
            int kill = Integer.compare(o1.get("kills").asInt(), o2.get("kills").asInt());
            if(kill != 0){
                return kill;
            }

            int kpm = Double.compare(o1.get("killsPerMinute").asDouble(), o2.get("killsPerMinute").asDouble());
            if(kpm != 0){
                return kpm;
            }

            int headshot = Double.compare(
                    o1.get("headshotKills").asDouble() / o1.get("kills").asDouble(),
                    o2.get("headshotKills").asDouble() / o2.get("kills").asDouble()
            );
            if(headshot != 0){
                return headshot;
            }

            int accuracy = Double.compare(
                    o1.get("shotsHit").asDouble() / o1.get("shotsFired").asDouble(),
                    o2.get("shotsHit").asDouble() / o2.get("shotsFired").asDouble()
            );
            if(accuracy != 0){
                return accuracy;
            }

            return Double.compare(
                    o1.get("hitVKills").asDouble(),
                    o2.get("hitVKills").asDouble()
            );

        }
    }

    private static final class VehicleComparator implements Comparator<JsonNode> {

        @Override
        public int compare(JsonNode o1, JsonNode o2) {
            int kill = Integer.compare(o1.get("kills").asInt(), o2.get("kills").asInt());
            if(kill != 0){
                return kill;
            }

            int kpm = Double.compare(o1.get("killsPerMinute").asDouble(), o2.get("killsPerMinute").asDouble());
            if(kpm != 0){
                return kpm;
            }

            return Integer.compare(
                    o1.get("destroyed").asInt(),
                    o2.get("destroyed").asInt()
            );

        }
    }

}
