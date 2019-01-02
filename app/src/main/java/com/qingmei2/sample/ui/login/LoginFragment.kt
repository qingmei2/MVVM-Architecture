package com.qingmei2.sample.ui.login

import com.qingmei2.rhine.base.view.fragment.BaseFragment
import com.qingmei2.rhine.ext.livedata.toReactiveStream
import com.qingmei2.sample.R
import com.qingmei2.sample.common.loadings.CommonLoadingViewModel
import com.qingmei2.sample.databinding.FragmentLoginBinding
import com.qingmei2.sample.ui.MainActivity
import com.uber.autodispose.autoDisposable
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(loginKodeinModule)
    }

    override val layoutId: Int = R.layout.fragment_login

    val viewModel: LoginViewModel by instance()

    val loadingViewModel: CommonLoadingViewModel by instance()

    override fun initView() {
        viewModel.userInfo
                .toReactiveStream()
                .doOnNext { toMain() }
                .autoDisposable(scopeProvider)
                .subscribe()

        viewModel.loadingLayout
                .toReactiveStream()
                .doOnNext { loadingViewModel.applyState(it) }
                .autoDisposable(scopeProvider)
                .subscribe()
    }

    fun login() = viewModel.login()

    fun toMain() = MainActivity.launch(activity!!)
}