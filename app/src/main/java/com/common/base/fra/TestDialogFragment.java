package com.common.base.fra;

import android.view.ViewGroup;
import com.common.base.R;
import com.common.base.databinding.TestDialogFragBinding;
import com.common.libbase.frag.BaseDialogFragment;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/12
 * @Description: java类作用描述
 */
public class TestDialogFragment extends BaseDialogFragment<TestDialogFragBinding> {

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.test_dialog_frag;
    }

    @Override
    public boolean getCancelable() {
        return true;
    }

    @Override
    public int getWidth() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    public int getHeight() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }
}
