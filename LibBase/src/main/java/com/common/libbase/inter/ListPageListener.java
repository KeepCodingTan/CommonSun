package com.common.libbase.inter;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * @Author: Sun
 * @CreateDate: 2022/7/6
 * @Description: java类作用描述
 */
public interface ListPageListener<T> {

     /**
      *获取recycleView
      * @return
      */
     RecyclerView getRecycleView();

     /**
      * 创建适配器
      * @return
      */
     BaseQuickAdapter<T, BaseViewHolder> createAdapter();

     /**
      * 创建布局管理器
      * @return
      */
     RecyclerView.LayoutManager setLayoutManager();
}
