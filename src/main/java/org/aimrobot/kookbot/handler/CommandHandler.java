package org.aimrobot.kookbot.handler;

import lombok.Getter;
import love.forte.simbot.component.kook.bot.KookBot;
import love.forte.simbot.component.kook.event.KookEvent;
import love.forte.simbot.definition.GuildMember;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: AimRobot-kookbot
 * @description:
 * @author: H4rry217
 **/

public abstract class CommandHandler {

    protected EventData eventData;
    protected final Map<String, String> params;

    public CommandHandler(EventData eventData, Map<String, String> params){
        this.eventData = eventData;
        this.params = params;
    }

    public CommandHandler(EventData eventData){
        this(eventData, new HashMap<>());
    }

    public KookEvent getEvent(){
        return this.eventData.getEvent();
    }

    public GuildMember getSender(){
        return this.eventData.getMember();
    }

    public KookBot getBot(){
        return this.getEvent().getBot();
    }

    public Map<String, String> getParams(){
        return this.params;
    }

    @Getter
    public static class EventData{

        private final KookEvent event;
        private final GuildMember member;

        public EventData(KookEvent event, GuildMember member){
            this.event = event;
            this.member = member;
        }

    }


}
