package com.common.base.repository;

import com.common.base.api.ApiService;
import com.common.base.entity.SignIn;
import com.common.libnet.RetrofitManager;
import io.reactivex.Observable;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/8
 * @Description: java类作用描述
 */
public class HttpRepository {

    private static HttpRepository instance;
    private final ApiService apiService;

    public static HttpRepository getInstance(){
        if(instance == null){
            synchronized (HttpRepository.class){
                if(instance == null){
                    instance = new HttpRepository();
                }
            }
        }
        return instance;
    }

    private HttpRepository(){
        apiService = RetrofitManager.createService(ApiService.class);
    }

    public Observable<SignIn> signIn(String key,String sign,String timestamps,String userId,String gradeId){
        return apiService.signIn(key,sign,timestamps,userId,gradeId).compose(RetrofitManager.schedulers());
    }

}
