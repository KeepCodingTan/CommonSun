package com.common.libnet;

import com.common.libnet.tools.HttpInterceptor;
import com.common.libnet.tools.ResponseConverterFactory;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/5 10:36
 * @Description: java类作用描述
 */
public class RetrofitManager {

    private static volatile Retrofit instance;

    private static Retrofit createRetrofit(){
        if(instance == null){
            synchronized (RetrofitManager.class){
                if(instance == null){
                    instance = buildRetrofit();
                }
            }
        }
        return instance;
    }

    private static Retrofit buildRetrofit(){
        return new Retrofit.Builder().baseUrl(NetWorkManager.getInstance().hostUrl())
                .addConverterFactory(ResponseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient())
                .build();
    }

    private static OkHttpClient httpClient(){
        return new OkHttpClient.Builder()
                //添加自定义拦截器
                .addInterceptor(new HttpInterceptor())
                //设置超时时间
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }


    public static <T> T createService(Class<T> service){
        return createRetrofit().create(service);
    }

    public static <T> ObservableTransformer<T, T> schedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
