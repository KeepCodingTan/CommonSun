package com.common.base.act;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.common.base.R;
import com.common.base.fra.TestFragment;
import com.common.libbase.act.BaseActivity;
import com.common.base.databinding.TestFragmentActBinding;
import com.common.libbase.vm.BaseViewModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/12
 * @Description: java类作用描述
 */
public class TestFragmentActivity extends BaseActivity<TestFragmentActBinding, BaseViewModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.test_fragment_act;
    }

    @Override
    protected void initViewModel() {

    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {
       initToolBar(true, 0, "测试fragment", 0, 0,null);
        List<TestFragment> list = new ArrayList<>();
        list.add(new TestFragment(1));
        list.add(new TestFragment(2));
        list.add(new TestFragment(3));
        FragmentStateAdapter adapter = new FragmentStateAdapter(getSupportFragmentManager(),getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return list.get(position);
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        };
        mDataBinding.vPager.setAdapter(adapter);
    }

    @Override
    protected void refresh() {

    }

    @Override
    protected void loadMore() {

    }

}
