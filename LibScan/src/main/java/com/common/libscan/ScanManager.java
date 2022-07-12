package com.common.libscan;

import java.util.Vector;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/9
 * @Description: java类作用描述
 */
public class ScanManager {

    private static volatile ScanManager instance;

    private Vector<Class<? extends ScanObserver>> observerVector;

    public static ScanManager getInstance(){
        if(instance == null){
            synchronized (ScanManager.class){
                if(instance == null){
                    instance = new ScanManager();
                }
            }
        }
        return instance;
    }

    private ScanManager (){
        observerVector = new Vector<>();
    }

    public void registerObserver(Class<? extends ScanObserver> observer){
        if(!observerVector.contains(observer)){
            observerVector.add(observer);
        }
    }

    public void unRegisterObserver(Class<? extends ScanObserver> observer){
        if(observerVector.contains(observer)){
            observerVector.remove(observer);
        }
    }

    public Vector<Class<? extends ScanObserver>> queryObservers(){
        return observerVector;
    }

}
