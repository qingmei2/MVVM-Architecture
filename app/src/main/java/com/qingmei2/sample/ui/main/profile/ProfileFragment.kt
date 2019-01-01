package com.qingmei2.sample.ui.main.profile

import com.qingmei2.rhine.base.view.fragment.BaseFragment
import com.qingmei2.sample.R
import com.qingmei2.sample.databinding.FragmentProfileBinding
import com.qingmei2.sample.utils.toast
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(profileKodeinModule)
    }

    val viewModel: ProfileViewModel by instance()

    override val layoutId: Int = R.layout.fragment_profile

    override fun initView() {

    }

    fun edit() = toast { "comming soon..." }
}