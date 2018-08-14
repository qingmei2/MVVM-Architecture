package com.qingmei2.rhine.ui.home;


import com.qingmei2.rhine.R;
import com.qingmei2.rhine.base.acitivty.BaseActivity;
import com.qingmei2.rhine.databinding.ActivityHomeBinding;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        binding.setView(this);
        binding.setViewModel(viewModel);
    }
}
