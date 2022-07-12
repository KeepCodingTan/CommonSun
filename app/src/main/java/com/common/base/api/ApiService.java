package com.common.base.api;

import com.common.base.entity.SignIn;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/8
 * @Description: java类作用描述
 */
public interface ApiService {


    @FormUrlEncoded
    @POST("/api/attendance/offline/query")
    Observable<SignIn> signIn(@Field("privateKey") String key,
                              @Field("sign") String sign,
                              @Field("timestamps") String timestamps,
                              @Field("userId") String userId,
                              @Field("gradeId") String gradeId);
}
