package com.common.libpush.jpush;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/15
 * @Description: java类作用描述
 */
public class PushMessage {

    public String message;

    public String contentType;

    public String extra;

    public String notificationExtras;

    public PushMessage(String notificationExtras){
        this.notificationExtras = notificationExtras;
    }

    public PushMessage(String message,String extra,String contentType){
        this.message = message;
        this.extra = extra;
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotificationExtras() {
        return notificationExtras;
    }

    public void setNotificationExtras(String notificationExtras) {
        this.notificationExtras = notificationExtras;
    }
}
