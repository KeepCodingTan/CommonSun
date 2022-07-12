package com.common.base.act;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.common.base.R;
import com.common.base.databinding.TestListActivityBinding;
import com.common.base.viewmodel.ListViewModel;
import com.common.libbase.act.AbsListActivity;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/11
 * @Description: java类作用描述
 */
public class TestListActivity extends AbsListActivity<String, TestListActivityBinding, ListViewModel> {

    @Override
    protected void init() {
        super.init();
        mViewModel.loadData();
    }

    @Override
    public RecyclerView getRecycleView() {
        return mDataBinding.recycle;
    }

    @Override
    public BaseQuickAdapter<String, BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_list) {
            @Override
            protected void convert(@NonNull BaseViewHolder holder, String s) {
                holder.setText(R.id.tvContent,s);
            }
        };
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.test_list_activity;
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(ListViewModel.class);
    }

    @Override
    protected void bindViewModel() {

    }
}
