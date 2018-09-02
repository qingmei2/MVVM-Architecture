package com.qingmei2.sample.login

import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseActivity
import com.qingmei2.sample.databinding.ActivityLoginBinding
import org.kodein.di.Kodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val kodein: Kodein by retainedKodein {
        extend(parentKodein)
        import(loginKodeinModule)
        bind<LoginActivity>() with instance(this@LoginActivity)
    }

    private val loginViewModel: LoginViewModel by instance()

    override val layoutId: Int = R.layout.activity_login

    private val viewDelegate: LoginViewDelegate = LoginViewDelegate(loginViewModel).apply {
        binding.delegate = this
    }

}