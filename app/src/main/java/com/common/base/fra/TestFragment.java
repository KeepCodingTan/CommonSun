package com.common.base.fra;

import android.os.Handler;
import android.view.View;
import com.common.base.R;
import com.common.base.databinding.TestFragmentBinding;
import com.common.libbase.frag.BaseFragment;
import com.common.libbase.vm.BaseViewModel;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/12
 * @Description: java类作用描述
 */
public class TestFragment extends BaseFragment<TestFragmentBinding, BaseViewModel> {

    private int mTag;

    public TestFragment(int tag){
        this.mTag = tag;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.test_fragment;
    }

    @Override
    protected void initViewModel() {

    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {
        Handler handler = new Handler();
        TestDialogFragment dialogFragment = new TestDialogFragment();
        mDataBinding.tvContent.setText("测试fragment"+mTag);
        mDataBinding.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogFragment.show(getChildFragmentManager(),"测试fragment");
                    }
                },2000);
            }
        });
    }

    @Override
    protected void refresh() {

    }

    @Override
    protected void loadMore() {

    }
}
