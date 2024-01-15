package org.aimrobot.kookbot.utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface AimRobotServerAPI {

    @POST("/robot/banPlayer")
    Call<ResponseBody> banPlayer(@Header("access-token") String token, @Body Map<String, Object> body);

    @POST("/robot/sendChat")
    Call<ResponseBody> sendChat(@Header("access-token") String token, @Body Map<String, Object> body);

    @POST("/robot/exceCommand")
    Call<ResponseBody> exceCommand(@Header("access-token") String token, @Body Map<String, Object> body);

    @POST("/robot/getRecentActivePlayers")
    Call<ResponseBody> getRecentActivePlayers(@Header("access-token") String token, @Body Map<String, Object> body);

    @POST("/robot/getServerIds")
    Call<ResponseBody> getServerIds();

}
