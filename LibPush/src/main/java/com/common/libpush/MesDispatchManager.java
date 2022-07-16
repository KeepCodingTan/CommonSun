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

    @SafeVarargs
    public final void registerObserver(Class<? extends MessageObserver>... observers){
         if(observers != null){
             for(Class<? extends MessageObserver> mo : observers){
                 if(!observerVector.contains(mo)){
                     observerVector.add(mo);
                 }
             }
         }
    }

    @SafeVarargs
    public final void unRegisterObserver(Class<? extends MessageObserver>... observers){
        if(observers != null){
            for(Class<? extends MessageObserver> mo : observers){
                if(observerVector.contains(mo)){
                    observerVector.remove(mo);
                }
            }
        }
    }

    public Vector<Class<? extends MessageObserver>> queryObservers(){
        return observerVector;
    }

}
