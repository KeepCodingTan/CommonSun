package com.common.libbase.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.common.libbase.config.CustomHolder;
import com.common.libbase.vm.BaseViewModel;
import com.common.libbase.R;
import com.common.libbase.config.LoadState;
import com.common.libbase.databinding.FragmentBaseBinding;
import com.common.libbase.databinding.ViewLoadErrorBinding;
import com.common.libbase.databinding.ViewLoadingBinding;
import com.common.libbase.databinding.ViewNoDataBinding;
import com.common.libbase.databinding.ViewNoNetworkBinding;
import com.gyf.immersionbar.components.ImmersionFragment;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;


/**
 * @Author: Sun
 * @CreateDate: 2022/7/5
 * @Description: java类作用描述
 */
public abstract class BaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel>
        extends ImmersionFragment {


    protected DB mDataBinding;

    protected VM mViewModel;

    private FragmentBaseBinding mFragmentBaseBinding;

    private ViewLoadingBinding mViewLoadingBinding;

    private ViewLoadErrorBinding mViewLoadErrorBinding;

    private ViewNoNetworkBinding mViewNoNetworkBinding;

    private ViewNoDataBinding mViewNoDataBinding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            handleArguments(args);
        }

        initViewModel();

        // ViewModel订阅生命周期事件
        if (mViewModel != null) {
            getLifecycle().addObserver(mViewModel);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentBaseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, container, false);
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutResId(),
                mFragmentBaseBinding.flContentContainer, true);
        bindViewModel();
        mDataBinding.setLifecycleOwner(this);
        initLoadState();

        initLiveData();
        initListener();
        init();

        return mFragmentBaseBinding.getRoot();
    }

    @Override
    public void initImmersionBar() {

    }

    public void initLiveData() {

    }

    public void initListener() {
        getRefreshLayout().setEnableRefresh(enableRefresh());
        getRefreshLayout().setEnableLoadMore(enableLoadMore());
        getRefreshLayout().setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                refresh();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadMore();
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
        return mFragmentBaseBinding.smartRefresh;
    }

    private void initLoadState() {
        if (mViewModel != null && isSupportLoad()) {
            mViewModel.loadState.observe(getViewLifecycleOwner(), new Observer<LoadState>() {
                @Override
                public void onChanged(LoadState loadState) {
                    switchLoadView(loadState);
                }
            });
        }
    }


    /**
     * 根据加载状态 ， 切换不同的View
     *
     * @param loadState
     */
    private void switchLoadView(LoadState loadState) {
        removeLoadView();

        switch (loadState) {
            case LOADING:
                if (mViewLoadingBinding == null) {
                    mViewLoadingBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_loading,
                            mFragmentBaseBinding.flContentContainer, false);
                }
                mFragmentBaseBinding.flContentContainer.addView(mViewLoadingBinding.getRoot());
                break;

            case NO_NETWORK:
                if (mViewNoNetworkBinding == null) {
                    mViewNoNetworkBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_no_network,
                            mFragmentBaseBinding.flContentContainer, false);
                    mViewNoNetworkBinding.setViewModel(mViewModel);
                }
                mFragmentBaseBinding.flContentContainer.addView(mViewNoNetworkBinding.getRoot());
                break;

            case NO_DATA:
                if (mViewNoDataBinding == null) {
                    mViewNoDataBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_no_data,
                            mFragmentBaseBinding.flContentContainer, false);
                }
                CustomHolder empty = emptyHolder();
                if(empty != null){
                    mViewNoDataBinding.stateRetry.setImageResource(empty.imageRes);
                    mViewNoDataBinding.emptyMessage.setText(empty.desc);
                }
                mFragmentBaseBinding.flContentContainer.addView(mViewNoDataBinding.getRoot());
                break;

            case ERROR:
                if (mViewLoadErrorBinding == null) {
                    mViewLoadErrorBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_load_error,
                            mFragmentBaseBinding.flContentContainer, false);
                    mViewLoadErrorBinding.setViewModel(mViewModel);
                }
                CustomHolder error = errorHolder();
                if(error != null){
                    mViewLoadErrorBinding.imgLoadError.setImageResource(error.imageRes);
                    mViewLoadErrorBinding.tvLoadError.setText(error.desc);
                }
                mFragmentBaseBinding.flContentContainer.addView(mViewLoadErrorBinding.getRoot());
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


    private void removeLoadView() {
        int childCount = mFragmentBaseBinding.flContentContainer.getChildCount();
        if (childCount > 1) {
            mFragmentBaseBinding.flContentContainer.removeViews(1, childCount - 1);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // ViewModel订阅生命周期事件
        if (mViewModel != null) {
            getLifecycle().removeObserver(mViewModel);
        }
        removeLoadView();
    }


    /**
     * 处理参数
     *
     * @param args 参数容器
     */
    protected void handleArguments(Bundle args) {

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
