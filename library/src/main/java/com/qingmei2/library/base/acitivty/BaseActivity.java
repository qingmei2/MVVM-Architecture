package com.qingmei2.library.base.acitivty;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.qingmei2.library.base.BaseViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public abstract class BaseActivity<B extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity {

    protected B binding;

    @Inject
    public V viewModel;

    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectIfNeeded();
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        initView();
        initData();
    }

    private void injectIfNeeded() {
        try {
            AndroidInjection.inject(this);
        } catch (IllegalArgumentException ignored) {
        }
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
        if (showing) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initView();

}
