package com.common.libpush.jpush;

import com.common.libpush.jpush.PushMessage;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/15
 * @Description: java类作用描述
 */
public interface PushMessageListener {

    void onNotifyMessageOpened(PushMessage pushMessage);

    void onMessage(PushMessage pushMessage);

}
