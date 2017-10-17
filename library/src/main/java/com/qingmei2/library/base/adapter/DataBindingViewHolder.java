package com.qingmei2.library.base.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by QingMei on 2017/10/17.
 * desc:
 */

public class DataBindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public final T dataBinding;

    public DataBindingViewHolder(T binding) {
        super(binding.getRoot());

        dataBinding = binding;
    }
}
