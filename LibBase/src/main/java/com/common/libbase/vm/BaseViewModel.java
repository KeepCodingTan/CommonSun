package com.common.libbase.vm;


import android.content.res.Resources;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.blankj.utilcode.util.ToastUtils;
import com.common.libbase.App;
import com.common.libbase.config.LoadState;
import com.common.libnet.tools.HttpConstants;
import com.common.libnet.tools.HttpException;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/5
 * @Description: java类作用描述
 */
public abstract class BaseViewModel extends ViewModel implements DefaultLifecycleObserver {

    public Resources resources;

    /**
     * 加载状态
     */
    public MutableLiveData<LoadState> loadState = new MutableLiveData<>();

    public MutableLiveData<String> errorMsg = new MutableLiveData<>("数据加载失败");


    /**
     * 重新加载数据。没有网络，点击重试时回调
     */
    public void reloadData() {

    }

    public Resources getResources() {
        if (resources == null) {
            resources = App.getContext().getResources();
        }
        return resources;
    }

    public void handleException(Throwable t){
        ToastUtils.showShort(t.getMessage());
        if(t instanceof HttpException){
            HttpException e = (HttpException) t;
            if(e.getCode() == HttpConstants.NO_NET){
                loadState.postValue(LoadState.NO_NETWORK);
            }
        }else {
            loadState.postValue(LoadState.ERROR);
        }
    }

}
