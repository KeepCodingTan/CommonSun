package com.common.libpush.jpush;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.common.libpush.MesDispatchManager;
import com.common.libpush.MessageObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import cn.jpush.android.api.JPushInterface;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/15
 * @Description: java类作用描述
 */
public class PushManager {

    private static final String TAG = PushManager.class.getSimpleName();

    private volatile static PushManager mInstance;

    private int tag = 1;

    private volatile Map<String, ArrayList<PushMessageListener>> cusMessageListener;

//    private volatile Vector<PushMessageListener> notificMessageListener;

    private List<MessageObserver> messageObservers = new ArrayList<>();

    public static PushManager getInstance(){
        if(mInstance == null){
            synchronized (PushMessage.class){
                if(mInstance == null){
                    mInstance = new PushManager();
                }
            }
        }
        return mInstance;
    }

    private PushManager(){
        cusMessageListener = new HashMap<>();
//        notificMessageListener = new Vector<>();
        queryOberser();
    }

    private void queryOberser() {
        Vector<Class<? extends MessageObserver>> classVector = MesDispatchManager.getInstance().queryObservers();
        for(Class<? extends MessageObserver> cls : classVector){
            try {
                messageObservers.add(cls.newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化极光推送
     * @param context
     */
    public void init(Context context){
        JPushInterface.setDebugMode(true);
        JPushInterface.init(context);
    }

    /**
     * 设置别名
     * @param context
     * @param alias
     */
    public void setAlias(Context context,String alias){
        if(TextUtils.isEmpty(alias)){
            JPushInterface.deleteAlias(context,tag++);
        }else {
            JPushInterface.setAlias(context,tag++,alias);
        }
    }

    /**
     * 注册自定义消息监听
     * @param messageType
     * @param listener
     */
    public void registerCustomMessageListener(String messageType,PushMessageListener listener){
        ArrayList<PushMessageListener> list = cusMessageListener.get(messageType);
        if(list == null){
            synchronized (PushManager.class){
                list = new ArrayList<>();
            }
        }
        list.add(listener);
        cusMessageListener.put(messageType,list);
    }

    /**
     * 取消注册自定义消息监听
     * @param messageType
     * @param listener
     */
    public void unRegisterCustomMessageListener(String messageType,PushMessageListener listener){
        ArrayList<PushMessageListener> list = cusMessageListener.get(messageType);
        if(list != null && list.contains(listener)){
            list.remove(listener);
        }
    }

    /**
     * 注册通知消息监听
     * @param listener
     */
    /*public void registerNotificMessageListener(PushMessageListener listener){
        if(!notificMessageListener.contains(listener)){
            notificMessageListener.add(listener);
        }
    }*/

    /**
     * 取消注册通知消息监听
     * @param listener
     */
    /*public void unRegisterNotificMessageListener(PushMessageListener listener){
        if(notificMessageListener.contains(listener)){
            notificMessageListener.remove(listener);
        }
    }*/

    /**
     * 点击通知消息回调
     * @param notificationExtras
     */
    public void onNotifyMessageOpened(String notificationExtras){
        if(TextUtils.isEmpty(notificationExtras)){
            Log.e(TAG,"message notificationExtras is null !");
            return;
        }
        Log.d(TAG,"onNotifyMessageOpened=="+notificationExtras);
        PushMessage pushMessage = new PushMessage(notificationExtras);
        /*if(notificMessageListener != null){
            for(PushMessageListener pml : notificMessageListener){
                pml.onNotifyMessageOpened(pushMessage);
            }
        }*/
        for(MessageObserver observer: messageObservers){
            if(observer.match(pushMessage)){
                observer.handle(pushMessage);
                break;
            }
        }
    }

    /**
     * 接收到自定义消息回调
     * @param message
     * @param extra
     * @param contentType
     */
    public void onMessage(String message,String extra,String contentType){
        if(TextUtils.isEmpty(message)){
            Log.e(TAG,"message is null !");
            return;
        }
        Log.d(TAG,"message=="+message);
        PushMessage pushMessage = new PushMessage(message,extra,contentType);
        ArrayList<PushMessageListener> list = cusMessageListener.get(contentType);
        if(list != null){
            for(PushMessageListener pml : list){
                pml.onMessage(pushMessage);
            }
        }
    }

}
