package com.common.base.act;

import android.content.Intent;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import com.common.base.BindClickListener;
import com.common.base.R;
import com.common.base.databinding.ActivityMainBinding;
import com.common.base.viewmodel.MainViewModel;
import com.common.libbase.act.BaseActivity;
import com.common.libscan.ScanCodeActivity;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/9
 * @Description: java类作用描述
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements BindClickListener {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setEvent(this);
    }

    @Override
    protected void init() {
        hideToolBar();
    }

    @Override
    protected void refresh() {

    }

    @Override
    protected void loadMore() {

    }

    @Override
    public void onViewClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnScan:
                intent = new Intent(MainActivity.this, ScanCodeActivity.class);
                startActivity(intent);
                break;
            case R.id.btnList:
                intent = new Intent(MainActivity.this, TestListActivity.class);
                startActivity(intent);
                break;
            case R.id.btnFrag:
                intent = new Intent(MainActivity.this, TestFragmentActivity.class);
                startActivity(intent);
                break;
        }
    }

}
