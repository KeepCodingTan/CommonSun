package com.common.base.act;

import androidx.lifecycle.ViewModelProvider;

import com.common.base.R;
import com.common.base.databinding.SignInActivityBinding;
import com.common.base.viewmodel.SignInViewModel;
import com.common.libbase.act.BaseActivity;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/8
 * @Description: java类作用描述
 */
public class SignInActvity extends BaseActivity<SignInActivityBinding, SignInViewModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.sign_in_activity;
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        String gradeId = "a2120226582A10D2NDlp";
        String privateKey = "vuPgVevkiM7AnzuyjrKYihIGLmFLfk8Fxc8IX3QJtZJc6UUddutOHCOIebZxCK8L";
        String sign = "706dd1cf9ca40c9a1597fdb495ec4a89";
        String timestamps = "1657266233944";
        String userId = "1452742";
        mViewModel.signIn(privateKey,sign,timestamps,userId,gradeId);
    }

    @Override
    protected void refresh() {

    }

    @Override
    protected void loadMore() {

    }
}
