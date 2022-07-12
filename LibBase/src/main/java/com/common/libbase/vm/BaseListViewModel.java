package com.common.libbase.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.common.libbase.config.LoadState;

import java.util.List;
import java.util.Objects;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/6
 * @Description: java类作用描述
 */
public abstract class BaseListViewModel<T> extends BaseViewModel{

    /**
     * 当前页码
     */
    protected int mPage = 1;
    /**
     * 页码大小
     */
    protected int mPageSize = 20;
    /**
     * 列表数据
     */
    protected MutableLiveData<List<T>> _listData = new MutableLiveData<>();

    public LiveData<List<T>> listData = _listData;

    public abstract void loadData();

    /**
     * 加载更多
     */
    public void loadMore(){
        mPage++;
        loadData();
    }

    /**
     * 刷新
     */
    public void refresh(){
        mPage = 1;
        loadData();
    }

    /**
     * 是否是第一页
     * @return
     */
    public boolean isFirstPage(){
        return mPage == 1;
    }

    /**
     * 是否是最后一页
     * @return
     */
    public boolean isLastPage(){
        if(listData.getValue() == null){
            return true;
        }else {
            return mPageSize > listData.getValue().size();
        }
    }

    /**
     * 是否没有数据
     * @return
     */
    public boolean isEmpty(){
        if(mPage == 1){
            if(listData.getValue() == null){
                return true;
            }else {
                return listData.getValue().isEmpty();
            }
        }else {
            return false;
        }
    }

}
