package com.common.libbase.frag;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.common.libbase.R;
import com.common.libbase.databinding.DialogFragmentBaseBinding;
import com.gyf.immersionbar.ImmersionBar;
import java.lang.reflect.Field;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/11
 * @Description: java类作用描述
 */
public abstract class BaseDialogFragment<DB extends ViewDataBinding> extends DialogFragment {

    private DialogFragmentBaseBinding dialogRootBinding;
    public DB mDataBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setStyle(STYLE_NORMAL,getStyle());
        super.onCreate(savedInstanceState);
    }

    private void setNoActionBar() {
        ImmersionBar.with(this)
                .statusBarView(dialogRootBinding.statusbar)
                .navigationBarColor(R.color.color_222222)
                .navigationBarDarkIcon(true)
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(R.color.color_white)
                .init();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog =  super.onCreateDialog(savedInstanceState);
        dialog.setOnKeyListener((dialogInterface, keyCode, keyEvent) -> {
            if(keyCode == KeyEvent.KEYCODE_BACK){
                return !getCancelable();
            }
            return false;
        });
        dialog.setCanceledOnTouchOutside(getCancelable());
        dialog.setCancelable(getCancelable());
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialogRootBinding = DataBindingUtil.inflate(inflater,R.layout.dialog_fragment_base,container,false);
        mDataBinding = DataBindingUtil.inflate(inflater,getLayoutId(),dialogRootBinding.flContentContainer,true);
        mDataBinding.setLifecycleOwner(this);
        return dialogRootBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setNoActionBar();
        initView();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = getWidth();
        params.height = getHeight();
        params.gravity = getGravity();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.getDecorView().setPadding(0,0,0,0);
        window.setAttributes(params);
        int anim = getAnim();
        if(anim != 0){
            window.setWindowAnimations(anim);
        }
        super.onStart();
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
//        super.show(manager, tag);
        try {
            Field mDismissed = DialogFragment.class.getDeclaredField("mDismissed");
            mDismissed.setAccessible(true);
            mDismissed.set(this,false);
            Field mShownByMe = DialogFragment.class.getDeclaredField("mShownByMe");
            mShownByMe.setAccessible(true);
            mShownByMe.set(this,true);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(this,tag);
        transaction.commitAllowingStateLoss();
    }

    public int getAnim(){
        return 0;
    }

    public int getGravity() {
        return Gravity.CENTER;
    }

    public  int getHeight() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    public int getWidth() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    public abstract void initView();

    public abstract int getLayoutId();

    public int getStyle() {
        return android.R.style.ThemeOverlay_Material_Dialog;
    }

    public boolean getCancelable() {
        return true;
    }

}
