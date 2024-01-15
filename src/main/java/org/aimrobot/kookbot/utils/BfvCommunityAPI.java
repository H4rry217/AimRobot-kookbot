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

public interface BfvCommunityAPI {

    @GET("/api/findPlayerStatus")
    Call<ResponseBody> findPlayerStatus(@Query("pid") Long playerId);

}
