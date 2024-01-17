package org.aimrobot.kookbot;

import love.forte.simboot.annotation.Listener;
import love.forte.simbot.ID;
import love.forte.simbot.component.kook.event.KookChannelMessageEvent;
import love.forte.simbot.component.kook.event.KookMessageBtnClickEvent;
import love.forte.simbot.component.kook.message.KookKMarkdownMessage;
import love.forte.simbot.definition.Channel;
import love.forte.simbot.definition.Guild;
import love.forte.simbot.definition.GuildMember;
import love.forte.simbot.kook.objects.kmd.AtTarget;
import love.forte.simbot.kook.objects.kmd.KMarkdownBuilder;
import love.forte.simbot.message.Message;
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
    public void onButtonClick(KookMessageBtnClickEvent event){
        String btnOwnerId = event.getSourceEvent().getTargetId();
        if(event.getBot().isMe(ID.$(btnOwnerId))){
            String[] values = event.getValue().split("\\[@\\]");

            Guild guild = event.getBot().getGuild(ID.$(values[0]));
            Channel channel = guild.getChannel(ID.$(values[1]));

            GuildMember member = channel.getMember(event.getUserId());
            //long time = Long.parseLong(values[2]);

            Message msg;

            if("CMD".equals(values[3])){
                channel.sendBlocking(new KookKMarkdownMessage(
                        new KMarkdownBuilder().at(new AtTarget.User(event.getUserId().toString())).text(" 点击按钮触发了指令 ").inlineCode(values[4]).build()
                ));

                if((msg = commandDispatcher.receive(values[4], event, member)) != null){
                    channel.sendAsync(msg);
                }
            }

        }

    }

    @Listener
    public void onChannelMessage(KookChannelMessageEvent event) {
        String content = event.getMessageContent().getPlainText().replace("\\", "");

        Message msg;
        if((msg = commandDispatcher.receive(content, event, event.getAuthor())) != null){
            event.replyAsync(msg);
        }
    }

}
