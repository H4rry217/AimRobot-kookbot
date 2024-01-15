package org.aimrobot.kookbot;

import love.forte.simboot.annotation.Listener;
import love.forte.simbot.component.kook.event.KookChannelMessageEvent;
import org.aimrobot.kookbot.handler.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @program: AimRobot-kookbot
 * @description:
 * @author: H4rry217
 **/

@Component
public class MessageListener {

    @Autowired
    private CommandDispatcher commandDispatcher;

    @Listener
    public void onChannelMessage(KookChannelMessageEvent event) {
        commandDispatcher.receive(event);
    }

}
