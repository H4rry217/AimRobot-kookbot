package org.aimrobot.kookbot.handler;

import love.forte.simbot.component.kook.event.KookChannelMessageEvent;
import love.forte.simbot.component.kook.event.KookMessageEvent;

import java.util.Map;

/**
 * @program: AimRobot-QQBot
 * @description:
 * @author: H4rry217
 **/

public class ChannelCommandHandler extends CommandHandler {

    public ChannelCommandHandler(KookChannelMessageEvent event, Map<String, String> params) {
        super(event, params);
    }

    public KookMessageEvent.Channel getChannel(){
        return (KookMessageEvent.Channel) this.messageEvent.getChannel();
    }

}
