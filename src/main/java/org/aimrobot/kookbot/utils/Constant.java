package org.aimrobot.kookbot.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: AimRobot-QQBot
 * @description:
 * @author: H4rry217
 **/

public class Constant {

    public static final Map<Integer, String> BFBAN_STATUS = new HashMap<>() {{
        put(0, "未处理");
        put(1, "实锤");
        put(2, "待自证");
        put(3, "MOSS自证");
        put(4, "无效举报");
        put(5, "讨论中");
        put(6, "待实锤(需要更多投票)");
        put(7, "数据异常自动石锤");
        put(8, "刷枪");
    }};

    public static final Map<Integer, String> BFV_COMMUNITY_STATUS = new HashMap<>() {{
        put(0, "数据正常");
        put(1, "举报证据不足[无效举报]");
        put(2, "武器数据异常");
        put(3, "全局黑名单[来自玩家的举报]");
        put(4, "全局白名单[刷枪或其它自证]");
        put(5, "全局白名单[Moss自证]");
        put(6, "当前数据正常(曾经有武器数据异常记录)");
        put(7, "全局黑名单[服主添加]");
        put(8, "永久全局黑名单[羞耻柱]");
        put(9, "永久全局黑名单[辱华涉政违法]");
        put(10, "全局黑名单[检查组添加]");
        put(11, "诈骗黑名单[不受欢迎的玩家]");
        put(12, "全局黑名单[机器人自动反外挂]");
        put(13, "全局黑名单[社区举报武器异常]");
    }};

}
