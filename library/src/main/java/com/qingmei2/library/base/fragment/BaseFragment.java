package com.qingmei2.library.base.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qingmei2.library.base.BaseViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by QingMei on 2017/10/12.
 * desc:
 */

public abstract class BaseFragment<B extends ViewDataBinding, V extends BaseViewModel> extends Fragment {

    protected B binding;

    @Inject
    public V viewModel;

    protected View mRootView;

    protected ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getContext()).inflate(getLayoutRes(), container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DataBindingUtil.bind(view);
        initView(view);
        initData();
    }

    @Override
    public void onAttach(Activity activity) {
        AndroidSupportInjection.inject(this);
        super.onAttach(activity);
    }

    protected void onStateChanged(BaseViewModel.State state) {
        switch (state) {
            case LOAD_WAIT:
                break;
            case LOAD_ING:
                loading(true);
                break;
            case LOAD_SUCCESS:
                loading(false);
                break;
            case LOAD_FAILED:
                loading(false);
                break;
            default:
                break;
        }
    }

    protected void loading(boolean showing) {
        if (getContext() != null) {
            if (showing) {
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setCancelable(false);
                progressDialog.show();
            } else if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }

    protected abstract int getLayoutRes();

    protected abstract void initView(View view);

    protected abstract void initData();

}
