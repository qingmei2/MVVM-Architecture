package com.qingmei2.library.ui.home;


import com.qingmei2.library.R;
import com.qingmei2.library.base.acitivty.BaseActivity;
import com.qingmei2.library.databinding.ActivityHomeBinding;

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
