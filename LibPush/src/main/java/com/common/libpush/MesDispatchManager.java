package com.common.libpush;

import java.util.Vector;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/15
 * @Description: java类作用描述
 */
public class MesDispatchManager {

    private static volatile MesDispatchManager instance;

    private Vector<Class<? extends MessageObserver>> observerVector;

    public static MesDispatchManager getInstance(){
        if(instance == null){
            synchronized (MesDispatchManager.class){
                if(instance == null){
                    instance = new MesDispatchManager();
                }
            }
        }
        return instance;
    }

    private MesDispatchManager (){
        observerVector = new Vector<>();
    }

    public void registerObserver(Class<? extends MessageObserver> observer){
        if(!observerVector.contains(observer)){
            observerVector.add(observer);
        }
    }

    public void unRegisterObserver(Class<? extends MessageObserver> observer){
        if(observerVector.contains(observer)){
            observerVector.remove(observer);
        }
    }

    public Vector<Class<? extends MessageObserver>> queryObservers(){
        return observerVector;
    }

}
