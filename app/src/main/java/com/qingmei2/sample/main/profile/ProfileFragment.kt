package com.qingmei2.sample.main.profile

import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseFragment
import com.qingmei2.sample.databinding.ActivityMainBinding

class ProfileFragment : BaseFragment<ActivityMainBinding, ProfileViewDelegate>() {

    override val viewDelegate: ProfileViewDelegate by lazy {
        ProfileViewDelegate()
    }

    override val layoutId: Int = R.layout.activity_main
}