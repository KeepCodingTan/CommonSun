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

    @SafeVarargs
    public final void registerObserver(Class<? extends ScanObserver>... observers){
        if(observers != null){
            for(Class<? extends ScanObserver> so : observers){
                if(!observerVector.contains(so)){
                    observerVector.add(so);
                }
            }
        }
    }

    @SafeVarargs
    public final void unRegisterObserver(Class<? extends ScanObserver>... observers){
        if(observers != null){
            for (Class<? extends ScanObserver> so : observers){
                if(observerVector.contains(so)){
                    observerVector.remove(so);
                }
            }
        }
    }

    public Vector<Class<? extends ScanObserver>> queryObservers(){
        return observerVector;
    }

}
