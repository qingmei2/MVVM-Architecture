package com.qingmei2.sample.main.profile

import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseFragment
import com.qingmei2.sample.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewDelegate = ProfileViewDelegate().apply {
        binding.delegate = this@apply
    }

    override val layoutId: Int = R.layout.fragment_profile
}