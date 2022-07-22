package com.common.libbase.act;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.common.libbase.inter.ListPageListener;
import com.common.libbase.vm.BaseListViewModel;

import java.util.List;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/6
 * @Description: java类作用描述
 */
public abstract class AbsListActivity<T,DB extends ViewDataBinding, VM extends BaseListViewModel<T>> extends BaseActivity<DB,VM> implements ListPageListener<T> {

    public RecyclerView.LayoutManager manager = setLayoutManager();

    public BaseQuickAdapter<T, BaseViewHolder> adapter = createAdapter();

    @Override
    public RecyclerView.LayoutManager setLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    protected void init() {
        getRecycleView().setLayoutManager(manager);
        getRecycleView().setAdapter(adapter);
    }

    @Override
    public boolean enableLoadMore() {
        return true;
    }

    @Override
    public boolean enableRefresh() {
        return true;
    }

    @Override
    public void initLiveData() {
        super.initLiveData();
        mViewModel.listData.observe(this, ts -> {
            if(!mViewModel.isEmpty()){
                onLoadSuccess(ts);
            }
        });
    }

    private void onLoadSuccess(List<T> ts) {
       if(mViewModel.isFirstPage()){
           getRefreshLayout().finishRefresh();
           adapter.setNewData(ts);
       }else {
           if(mViewModel.isLastPage()){
               getRefreshLayout().finishLoadMoreWithNoMoreData();
           }else {
               getRefreshLayout().finishLoadMore();
           }
           adapter.addData(ts);
       }
    }

    @Override
    protected void refresh() {
        mViewModel.refresh();
    }

    @Override
    protected void loadMore() {
        mViewModel.loadMore();
    }
}
