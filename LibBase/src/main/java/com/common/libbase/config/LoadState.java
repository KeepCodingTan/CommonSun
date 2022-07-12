package com.common.libbase.config;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/5
 * @Description: java类作用描述
 */
public enum LoadState {

    /**
     * 加载中
     */
    LOADING,

    /**
     * 没有网络
     */
    NO_NETWORK,

    /**
     * 没有数据
     */
    NO_DATA,

    /**
     * 加载出错
     */
    ERROR,

    /**
     * 加载成功
     */
    SUCCESS
}
