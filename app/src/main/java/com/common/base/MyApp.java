package com.common.base;

import com.common.base.common.FinalObserver;
import com.common.base.common.SignInObserver;
import com.common.base.entity.BaseResponse;
import com.common.libbase.App;
import com.common.libnet.NetWorkManager;
import com.common.libnet.inter.NetWorkInterface;
import com.common.libnet.tools.HttpConstants;
import com.common.libnet.tools.HttpException;
import com.common.libscan.ScanManager;
import com.google.gson.Gson;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/7
 * @Description: java类作用描述
 */
public class MyApp extends App {


    @Override
    public void onCreate() {
        super.onCreate();
        initNetWork();
        registerScanObserver();
    }

    private void registerScanObserver() {
        ScanManager.getInstance().registerObserver(SignInObserver.class);
        ScanManager.getInstance().registerObserver(FinalObserver.class);
    }

    private void initNetWork() {
        NetWorkManager.init(new NetWorkInterface() {
            @Override
            public String hostUrl() {
                return Constants.HOST_PRE;
            }

            @Override
            public String userToken() {
                return "96445a3694643d43e9bcdf0d40005b80";
            }

            @Override
            public String analysisHeader(Gson gson, String response) {
                BaseResponse baseResponse = gson.fromJson(response,BaseResponse.class);
                if(baseResponse.success){
                    return gson.toJson(baseResponse.entity);
                }else {
                    throw new HttpException(HttpConstants.REQUEST_FAIL,baseResponse.message);
                }
            }
        });
    }
}
