package com.qingmei2.sample.ui.main.profile

import com.qingmei2.rhine.base.view.BaseFragment
import com.qingmei2.sample.R
import com.qingmei2.sample.databinding.FragmentProfileBinding
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(profileKodeinModule)
    }

    override val viewDelegate: ProfileViewDelegate by instance()

    override val layoutId: Int = R.layout.fragment_profile

    override fun initView() {
        binding.delegate = viewDelegate
    }
}