package com.common.libpush.jpush;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/19
 * @Description: java类作用描述
 */
public interface PushInterceptor {

    boolean intercept(PushMessage message);

}
