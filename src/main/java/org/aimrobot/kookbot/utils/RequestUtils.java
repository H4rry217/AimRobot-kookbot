package org.aimrobot.kookbot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.aimrobot.kookbot.BotConfig;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: AimRobot-QQBot
 * @description:
 * @author: H4rry217
 **/

public class RequestUtils {
    private static final RequestUtils INSTANCE = new RequestUtils();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static RequestUtils getInstance(){
        return INSTANCE;
    }

    @Getter
    private final AimRobotServerAPI remoteServer;

    @Getter
    private final GametoolAPI gametoolAPI;

    @Getter
    private final BfvCommunityAPI bfvCommunityAPI;

    public RequestUtils(){
        this.remoteServer = new Retrofit.Builder()
                .baseUrl(SpringUtils.getBean(BotConfig.class).getApiUrl())
                .client(new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build())
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(AimRobotServerAPI.class);

        this.gametoolAPI = new Retrofit.Builder()
                .baseUrl("https://api.gametools.network")
                .client(new OkHttpClient.Builder().connectTimeout(5000, TimeUnit.MILLISECONDS).build())
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(GametoolAPI.class);

        this.bfvCommunityAPI = new Retrofit.Builder()
                .baseUrl("https://api.zth.ink")
                .client(new OkHttpClient.Builder().connectTimeout(5000, TimeUnit.MILLISECONDS).build())
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(BfvCommunityAPI.class);
    }

    public static JsonNode getJsonNode(String body){
        try {
            return OBJECT_MAPPER.readTree(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //public List<String> getConnectServerIds(){
    //    AimRobotServerAPI api = getRemoteServer();
    //    Call<ResponseBody> call = api.getServerIds();
    //
    //    List<String> list = new ArrayList<>();
    //    try {
    //        Response<ResponseBody> execute = call.execute();
    //        JsonNode jsonNode = RequestUtils.getJsonNode(execute.body().string());
    //
    //        jsonNode.get("data").get("result").elements().forEachRemaining((node) -> {
    //            list.add(node.asText());
    //        });
    //
    //
    //    } catch (Exception ignored){
    //
    //    }
    //
    //    return list;
    //}

}
