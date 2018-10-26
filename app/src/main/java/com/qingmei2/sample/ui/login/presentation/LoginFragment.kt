package com.qingmei2.sample.ui.login.presentation

import android.os.Bundle
import android.view.View
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseApplication
import com.qingmei2.sample.base.BaseFragment
import com.qingmei2.sample.databinding.FragmentLoginBinding
import com.qingmei2.sample.ui.login.di.loginKodeinModule
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(BaseApplication.INSTANCE.kodein)
        import(loginKodeinModule)
        bind<LoginFragment>() with instance(this@LoginFragment)
    }

    override val layoutId: Int = R.layout.fragment_login

    private val viewDelegate: LoginViewDelegate by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.delegate = viewDelegate
    }
}