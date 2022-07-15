package com.common.libpush;

import com.common.libpush.jpush.PushMessage;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/15
 * @Description: java类作用描述
 */
public interface MessageObserver {

    boolean match(PushMessage message);


    void handle(PushMessage message);

}
