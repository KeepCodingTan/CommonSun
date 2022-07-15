package com.common.libpush.jpush;

import android.content.Context;
import android.util.Log;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/15
 * @Description: java类作用描述
 */
public class CustomJPushMessageReceiver extends JPushMessageReceiver {

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
//        super.onNotifyMessageOpened(context, notificationMessage);
        Log.d("sun","onNotifyMessageOpened = "+notificationMessage.toString());
        PushManager.getInstance().onNotifyMessageOpened(notificationMessage.notificationExtras);
    }

    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
//        super.onMessage(context, customMessage);
        Log.d("sun","onMessage = "+customMessage.toString());
        PushManager.getInstance().onMessage(customMessage.message,customMessage.extra,customMessage.contentType);
    }
}
