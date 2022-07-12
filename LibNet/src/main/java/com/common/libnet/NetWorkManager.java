package com.common.libnet;

import com.common.libnet.inter.NetWorkInterface;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/5
 * @Description: java类作用描述
 */
public class NetWorkManager {

    private static NetWorkInterface mInstance;

    public static void init(NetWorkInterface instance){
        mInstance = instance;
    }

    public static NetWorkInterface getInstance(){
        return mInstance;
    }

}
