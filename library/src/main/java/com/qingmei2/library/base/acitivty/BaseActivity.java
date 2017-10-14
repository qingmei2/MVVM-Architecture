package com.qingmei2.library.base.acitivty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dagger.android.AndroidInjection;

public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {

    protected B binding;

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

    protected void loadingDialog(boolean showing) {
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

    public void onBackClicked() {
        finish();
    }

    public void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }
}
