package com.common.libbase;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;
import com.blankj.utilcode.util.Utils;
import com.tencent.mmkv.MMKV;


/**
 * @Author: Sun
 * @CreateDate: 2022/7/5
 * @Description: java类作用描述
 */
public class App extends Application {
    private static App context;
    public static boolean firstOpen;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        context = this;
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        firstOpen = true;
        init();
    }

    public static Context getContext() {
        return context;
    }


    /**
     * 一些第三方库和本地代码的初始化设置
     */
    private void init() {
        //bankj
        Utils.init(this);
        //mmkv
        MMKV.initialize(this);
    }

}
