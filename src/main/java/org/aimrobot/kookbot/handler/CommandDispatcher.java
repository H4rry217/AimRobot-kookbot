package org.aimrobot.kookbot.handler;

import love.forte.simbot.ID;
import love.forte.simbot.component.kook.event.KookChannelMessageEvent;
import love.forte.simbot.event.GroupMessageEvent;
import love.forte.simbot.event.MessageEvent;
import love.forte.simbot.message.Message;
import love.forte.simbot.message.Text;
import org.aimrobot.kookbot.BotConfig;
import org.aimrobot.kookbot.handler.builtin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: AimRobot-kookbot
 * @description:
 * @author: H4rry217
 **/

@Component
public class CommandDispatcher {

    private final Logger logger = LoggerFactory.getLogger(CommandDispatcher.class);

    @Autowired
    private BotConfig botConfig;

    private final Map<String, List<CommandListener>> LISTENERS = new HashMap<>();

    public CommandDispatcher(){
        registerListener(new BanPlayerCommand());
        registerListener(new SendChatCommand());
        registerListener(new ServerCommand());
        registerListener(new QueryPlayerCommand());
        registerListener(new QueryServerCommand());
        registerListener(new RemoteOperateCommand());
        registerListener(new RecentActivePlayerCommand());
        //registerListener(new BotStatusCommand());
    }

    public void registerListener(CommandListener commandListener){
        if(commandListener.getCommandKeyword() != null && !commandListener.getCommandKeyword().isBlank()){
            synchronized (LISTENERS){
                List<CommandListener> listeners = LISTENERS.getOrDefault(commandListener.getCommandKeyword(), new ArrayList<>());
                listeners.add(commandListener);

                LISTENERS.put(commandListener.getCommandKeyword(), listeners);
            }
        }
    }

    public void receive(MessageEvent messageEvent){
        String content = messageEvent.getMessageContent().getPlainText().replace("\\", "");
        logger.info("COMMAND -> {}", content);

        if(botConfig.getCommandPrefix().length() == 0 || content.startsWith(botConfig.getCommandPrefix())){
            String part2 = content.substring(botConfig.getCommandPrefix().length());

            StringBuilder keywordBuilder = new StringBuilder();
            CommandListener.CommandStyle commandStyle = null;

            int equalSignIndex = 0;
            int greaterThanIndex = 0;

            Map<String, String> paramMap = new HashMap<>();

            for (int i = 0; i < part2.length(); i++) {
                char chr = part2.charAt(i);

                if(chr == '='){
                    commandStyle = CommandListener.CommandStyle.Style1;
                    equalSignIndex = i;
                }else if(chr == '>'){
                    greaterThanIndex = i;
                    commandStyle = CommandListener.CommandStyle.Style1;
                }else if(chr == ' '){
                    commandStyle = CommandListener.CommandStyle.Style2;
                }else{
                    keywordBuilder.append(chr);
                    continue;
                }

                break;
            }

            String keyword = keywordBuilder.toString();

            if(LISTENERS.containsKey(keyword)) {
                if (commandStyle != null) {

                    switch (commandStyle) {
                        case Style1:

                            if (greaterThanIndex == 0) {
                                paramMap.put("param", part2.substring(equalSignIndex + 1));
                            } else {
                                StringBuilder toBuilder = new StringBuilder();

                                for (int i = greaterThanIndex + 1; i < part2.length(); i++) {
                                    if (part2.charAt(i) == '=') {
                                        paramMap.put("param", part2.substring(i + 1));
                                        paramMap.put("to", toBuilder.toString());
                                    } else {
                                        toBuilder.append(part2.charAt(i));
                                    }
                                }

                            }

                            break;
                        case Style2:
                            paramMap.putAll(
                                    getArgs(
                                            part2.substring(part2.indexOf(' ') == -1 ? part2.length() : part2.indexOf(' '))
                                    )
                            );
                            break;
                    }
                }

                CommandHandler commandHandler = new ChannelCommandHandler((KookChannelMessageEvent) messageEvent, paramMap);

                if(paramMap.size() > 0){
                    StringBuilder logBuilder = new StringBuilder("ARGS -> ");
                    paramMap.forEach((key, value) -> logBuilder.append('"').append(key).append('"').append(": ").append('"').append(value).append('"').append("     "));
                    logger.warn(logBuilder.toString());
                }

                for (CommandListener commandListener : LISTENERS.get(keyword)) {
                    if(commandListener.isGuildLimit() && !((KookChannelMessageEvent) messageEvent).getAuthor().getGuildId().equals(ID.$(botConfig.getGuildIdLimit()))){
                        messageEvent.replyAsync(Text.of("该指令无权在当前Kook服务器执行!"));
                    }else{
                        Message reply;
                        if((reply = commandListener.processEvent(commandHandler)) != null){
                            messageEvent.replyAsync(reply);
                        }
                    }
                }
            }

        }

    }

    public static Map<String, String> getArgs(String paramsString){
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < paramsString.length();) {
            if(paramsString.charAt(i) == '-' && paramsString.charAt(i + 1) == '-'){
                //arg identical
                i += 2;
                String argName = readArgName(paramsString.substring(i));

                i += argName.length();
                if(i == paramsString.length()){
                    i--;
                    map.put(argName, argName);
                }

                char chr = paramsString.charAt(i);
                //判断是否是有值的
                if(chr == ' '){
                    map.put(argName, argName);
                }else if(chr == '='){
                    i++;
                    String argVal = readArgValue(paramsString.substring(i));
                    map.put(argName, argVal);
                }

            }else{
                i++;
            }
        }

        return map;
    }

    private static String readArgName(String str){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if(chr == '=' || chr == ' '){
                break;
            }else {
                stringBuilder.append(chr);
            }
        }

        return stringBuilder.toString();
    }

    private static String readArgValue(String str){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if(chr == '"'){
                String strTemp = str.substring(i + 1);
                stringBuilder.append(strTemp, 0, strTemp.indexOf('"'));
                break;
            } else if (chr != ' ') {
                stringBuilder.append(chr);
            } else{
                break;
            }
        }

        return stringBuilder.toString();
    }

}
