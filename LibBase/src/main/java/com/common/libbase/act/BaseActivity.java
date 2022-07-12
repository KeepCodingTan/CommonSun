package com.common.libbase.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import com.common.libbase.config.CustomHolder;
import com.common.libbase.inter.ToolBarClickListener;
import com.common.libbase.vm.BaseViewModel;
import com.common.libbase.R;
import com.common.libbase.config.LoadState;
import com.common.libbase.databinding.ActivityBaseBinding;
import com.common.libbase.databinding.ViewLoadErrorBinding;
import com.common.libbase.databinding.ViewLoadingBinding;
import com.common.libbase.databinding.ViewNoDataBinding;
import com.common.libbase.databinding.ViewNoNetworkBinding;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/5
 * @Description: java类作用描述
 */
public abstract class BaseActivity<DB extends ViewDataBinding, VM extends BaseViewModel>
        extends AppCompatActivity {

    public DB mDataBinding;

    protected VM mViewModel;

    private ActivityBaseBinding mActivityBaseBinding;

    private ViewLoadingBinding mViewLoadingBinding;

    private ViewLoadErrorBinding mViewLoadErrorBinding;

    private ViewNoNetworkBinding mViewNoNetworkBinding;

    private ViewNoDataBinding mViewNoDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());

        mActivityBaseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
        mDataBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutResId(),
                mActivityBaseBinding.flContentContainer, true);

        if (isNoActionBar()) {
            setNoActionBar();
        }

        initViewModel();
        bindViewModel();

        mDataBinding.setLifecycleOwner(this);

        initLoadState();
        initLiveData();
        initListener();
        init();

        // ViewModel订阅生命周期事件
        if (mViewModel != null) {
            getLifecycle().addObserver(mViewModel);
        }
    }

    public void initListener() {
        getRefreshLayout().setEnableRefresh(enableRefresh());
        getRefreshLayout().setEnableLoadMore(enableLoadMore());
        getRefreshLayout().setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                loadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refresh();
            }
        });
    }

    public boolean enableRefresh(){
        return false;
    }

    public boolean enableLoadMore(){
        return false;
    }

    public SmartRefreshLayout getRefreshLayout(){
        return mActivityBaseBinding.smartRefresh;
    }

    public void initLiveData() {

    }

    /**
     * 设置沉浸式状态栏
     */
    private void setNoActionBar() {
        ImmersionBar.with(this)
                .statusBarView(mActivityBaseBinding.statusbar)
                .navigationBarColor(R.color.color_222222)
                .navigationBarDarkIcon(true)
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(R.color.color_white)
                .init();
    }

    /**
     * 更新状态栏
     * @param statusBarColor
     * @param statusBarDarkFont
     */
    public void updateStatusBar(int statusBarColor,boolean statusBarDarkFont){
        ImmersionBar.with(this).reset()
                .statusBarView(mActivityBaseBinding.statusbar)
                .navigationBarColor(R.color.color_222222)
                .navigationBarDarkIcon(true)
                .statusBarDarkFont(statusBarDarkFont, 0.2f)
                .statusBarColor(statusBarColor)
                .init();
    }

    /**
     *隐藏toolbar
     */
    public void hideToolBar(){
        mActivityBaseBinding.toolbar.getRoot().setVisibility(View.GONE);
        mActivityBaseBinding.viewLine.setVisibility(View.GONE);
    }

    /**
     * 自定义toolbar
     * @param showBackIcon  是否展示返回按钮
     * @param backRes   返回按钮资源图片
     * @param title  标题内容
     * @param titleColor  标题颜色
     * @param menuResId  菜单按钮资源图片
     * @param toolBarClickListener  点击事件回调
     */
    public void initToolBar(boolean showBackIcon, int backRes, String title, int titleColor, int menuResId, ToolBarClickListener toolBarClickListener){
        if(backRes == 0){
            backRes = R.drawable.icon_back_black;
        }
        if(titleColor == 0){
            titleColor = R.color.col_333333;
        }
        if(showBackIcon){
            mActivityBaseBinding.toolbar.getRoot().setNavigationIcon(backRes);
            mActivityBaseBinding.toolbar.getRoot().setNavigationOnClickListener(view -> {
                onBackPressed();
                if(toolBarClickListener != null){
                    toolBarClickListener.onBackIconClick();
                }
            });
        }else {
            mActivityBaseBinding.toolbar.getRoot().setNavigationIcon(null);
        }
        mActivityBaseBinding.toolbar.tvTitle.setText(title);
        mActivityBaseBinding.toolbar.tvTitle.setTextColor(getResources().getColor(titleColor));
        if(menuResId == 0){
            mActivityBaseBinding.toolbar.imgRightIcon.setVisibility(View.GONE);
        }else {
            mActivityBaseBinding.toolbar.imgRightIcon.setVisibility(View.VISIBLE);
            mActivityBaseBinding.toolbar.imgRightIcon.setOnClickListener(view -> {
                if(toolBarClickListener != null){
                    toolBarClickListener.onMenuIconClick();
                }
            });
        }
    }

    private void initLoadState() {
        if (mViewModel != null && isSupportLoad()) {
            mViewModel.loadState.observe(this, new Observer<LoadState>() {
                @Override
                public void onChanged(LoadState loadState) {
                    switchLoadView(loadState);
                }
            });
        }
    }

    private void removeLoadView() {
        int childCount = mActivityBaseBinding.flContentContainer.getChildCount();
        if (childCount > 1) {
            mActivityBaseBinding.flContentContainer.removeViews(1, childCount - 1);
        }
    }

    private void switchLoadView(LoadState loadState) {
        removeLoadView();

        switch (loadState) {
            case LOADING:
                if (mViewLoadingBinding == null) {
                    mViewLoadingBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_loading,
                            mActivityBaseBinding.flContentContainer, false);
                }
                mActivityBaseBinding.flContentContainer.addView(mViewLoadingBinding.getRoot());
                break;

            case NO_NETWORK:
                if (mViewNoNetworkBinding == null) {
                    mViewNoNetworkBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_no_network,
                            mActivityBaseBinding.flContentContainer, false);
                    mViewNoNetworkBinding.setViewModel(mViewModel);
                }
                mActivityBaseBinding.flContentContainer.addView(mViewNoNetworkBinding.getRoot());
                break;

            case NO_DATA:
                if (mViewNoDataBinding == null) {
                    mViewNoDataBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_no_data,
                            mActivityBaseBinding.flContentContainer, false);
                }
                CustomHolder empty = emptyHolder();
                if(empty != null){
                    mViewNoDataBinding.stateRetry.setImageResource(empty.imageRes);
                    mViewNoDataBinding.emptyMessage.setText(empty.desc);
                }
                mActivityBaseBinding.flContentContainer.addView(mViewNoDataBinding.getRoot());
                break;

            case ERROR:
                if (mViewLoadErrorBinding == null) {
                    mViewLoadErrorBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_load_error,
                            mActivityBaseBinding.flContentContainer, false);
                }
                CustomHolder error = errorHolder();
                if(error != null){
                    mViewLoadErrorBinding.imgLoadError.setImageResource(error.imageRes);
                    mViewLoadErrorBinding.tvLoadError.setText(error.desc);
                }
                mActivityBaseBinding.flContentContainer.addView(mViewLoadErrorBinding.getRoot());
                break;

            default:
                break;
        }
    }

    /**
     * 自定义空态页
     * @return
     */
    public CustomHolder emptyHolder(){
        return null;
    }

    /**
     * 自定义错误页
     * @return
     */
    public CustomHolder errorHolder(){
        return null;
    }

    /**
     * 处理参数
     *
     * @param intent 参数容器
     */
    protected void handleIntent(Intent intent) {

    }

    /**
     * 是否为沉浸模式
     *
     * @return true表示支持，false表示不支持
     */
    protected boolean isNoActionBar() {
        return true;
    }


    /**
     * 是否支持页面加载。默认不支持
     *
     * @return true表示支持，false表示不支持
     */
    protected boolean isSupportLoad() {
        return false;
    }

    /**
     * 获取当前页面的布局资源ID
     *
     * @return 布局资源ID
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化ViewModel
     */
    protected abstract void initViewModel();

    /**
     * 绑定ViewModel
     */
    protected abstract void bindViewModel();

    /**
     * 初始化
     */
    protected abstract void init();

    /**
     * 刷新
     */
    protected abstract void refresh();

    /**
     * 加载更多
     */
    protected abstract void loadMore();
}
