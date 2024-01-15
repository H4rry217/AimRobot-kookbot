package org.aimrobot.kookbot.utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @program: AimRobot-QQBot
 * @description:
 * @author: H4rry217
 **/

public interface GametoolAPI {

    @GET("/bfv/all")
    Call<ResponseBody> getPlayerData(@Query("name") String name, @Query("lang") String lang);

    @GET("/bfv/servers?region=all&platform=pc&limit=20&lang=zh-cn")
    Call<ResponseBody> queryServers(@Query("name") String name);

    @GET("/bfban/checkban")
    Call<ResponseBody> checkban(@Query("personaids") Long playerId);

}
