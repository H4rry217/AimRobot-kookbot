package org.aimrobot.kookbot.handler;

import love.forte.simbot.component.kook.KookMember;
import love.forte.simbot.component.kook.bot.KookBot;
import love.forte.simbot.component.kook.event.KookChannelMessageEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: AimRobot-kookbot
 * @description:
 * @author: H4rry217
 **/

public abstract class CommandHandler {

    protected KookChannelMessageEvent messageEvent;
    protected final Map<String, String> params;

    public CommandHandler(KookChannelMessageEvent event, Map<String, String> params){
        this.messageEvent = event;
        this.params = params;
    }

    public CommandHandler(KookChannelMessageEvent event){
        this(event, new HashMap<>());
    }

    public KookChannelMessageEvent getEvent(){
        return this.messageEvent;
    }

    public KookMember getSender(){
        return this.getEvent().getAuthor();
    }

    public KookBot getBot(){
        return this.getEvent().getBot();
    }

    public Map<String, String> getParams(){
        return this.params;
    }


}
