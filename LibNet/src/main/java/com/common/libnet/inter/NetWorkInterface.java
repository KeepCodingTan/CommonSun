package com.common.libnet.inter;

import com.google.gson.Gson;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/5
 * @Description: java类作用描述
 */
public interface NetWorkInterface {

    /**
     * 获取主机地址
     * @return
     */
    String hostUrl();


    /**
     * 获取用户的token
     * @return
     */
    String userToken();


    /**
     * 数据头解析
     * @return
     */
    String analysisHeader(Gson gson,String response);

}
