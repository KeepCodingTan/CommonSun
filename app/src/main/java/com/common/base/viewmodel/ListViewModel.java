package com.common.base.viewmodel;

import android.os.Handler;

import com.common.libbase.vm.BaseListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/11
 * @Description: java类作用描述
 */
public class ListViewModel extends BaseListViewModel<String> {

    @Override
    public void loadData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> list = new ArrayList<>();
                for (int i = 0;i<20;i++){
                    list.add("测试数据"+i);
                }
                _listData.postValue(list);
            }
        },3000);
    }

}
