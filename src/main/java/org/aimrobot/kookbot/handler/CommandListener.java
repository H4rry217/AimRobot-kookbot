package org.aimrobot.kookbot.handler;

import lombok.Getter;
import love.forte.simbot.message.Message;

/**
 * @program: AimRobot-kookbot
 * @description:
 * @author: H4rry217
 **/

public interface CommandListener {

    public Message processEvent(CommandHandler commandHandler);

    public String getCommandKeyword();

    public CommandStyle getCommandStyle();

    public default boolean isGuildLimit(){
        return false;
    }

    public enum CommandStyle{

        Style1("cmd=param"),
        Style2("cmd --paramKey=paramVal");

        @Getter
        private final String description;

        CommandStyle(String description){
            this.description = description;
        }

    }

}
