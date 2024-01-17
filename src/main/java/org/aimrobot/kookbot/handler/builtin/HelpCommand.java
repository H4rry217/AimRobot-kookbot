package org.aimrobot.kookbot.handler.builtin;

import love.forte.simbot.component.kook.message.KookKMarkdownMessage;
import love.forte.simbot.kook.objects.kmd.AtTarget;
import love.forte.simbot.kook.objects.kmd.KMarkdownBuilder;
import love.forte.simbot.message.Message;
import org.aimrobot.kookbot.BotConfig;
import org.aimrobot.kookbot.handler.CommandDispatcher;
import org.aimrobot.kookbot.handler.CommandHandler;
import org.aimrobot.kookbot.handler.CommandListener;
import org.aimrobot.kookbot.utils.SpringUtils;

import java.util.Map;

/**
 * @program: AimRobot-kookbot
 * @description:
 * @author: H4rry217
 **/

public class HelpCommand implements CommandListener {

    @Override
    public Message processEvent(CommandHandler commandHandler) {

        var set = SpringUtils.getBean(CommandDispatcher.class).getCommandListener();
        var builder = new KMarkdownBuilder();
        var botConfig = SpringUtils.getBean(BotConfig.class);

        builder.bold("当前机器人共有 "+set.size()+ " 条指令").newLine();
        for (Map.Entry<String, CommandListener> entry : set) {
            builder.inlineCode(botConfig.getCommandPrefix() + entry.getKey()).newLine();
        }

        builder.newLine().text("AimRobotLite项目: ").link("https://github.com/H4rry217/AimRobotLite");

        return new KookKMarkdownMessage(builder.build());
    }

    @Override
    public String getCommandKeyword() {
        return "help";
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
