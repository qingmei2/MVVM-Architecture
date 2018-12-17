package com.qingmei2.sample.ui.login

import com.qingmei2.rhine.base.view.fragment.BaseFragment
import com.qingmei2.sample.R
import com.qingmei2.sample.databinding.FragmentLoginBinding
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(loginKodeinModule)
    }

    override val layoutId: Int = R.layout.fragment_login

    override val viewDelegate: LoginViewDelegate by instance()

    override fun initView() {
        binding.delegate = viewDelegate
    }
}