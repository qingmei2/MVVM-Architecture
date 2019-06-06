package com.qingmei2.sample.ui.login

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.qingmei2.rhine.base.view.fragment.BaseFragment
import com.qingmei2.rhine.ext.reactivex.clicksThrottleFirst
import com.qingmei2.rhine.util.RxSchedulers
import com.qingmei2.sample.R
import com.qingmei2.sample.ui.MainActivity
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.fragment_login.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class LoginFragment : BaseFragment() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(loginKodeinModule)
    }

    override val layoutId: Int = R.layout.fragment_login

    private val mViewModel: LoginViewModel by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binds()
    }

    private fun binds() {
        mViewModel.loginIndicatorVisibleSubject
                .map { if (it) View.VISIBLE else View.GONE }
                .observeOn(RxSchedulers.ui)
                .autoDisposable(scopeProvider)
                .subscribe { mProgressBar.visibility = it }
        mViewModel.userInfoSubject
                .observeOn(RxSchedulers.ui)
                .autoDisposable(scopeProvider)
                .subscribe { MainActivity.launch(activity!!) }
        mViewModel.autoLoginEventSubject
                .observeOn(RxSchedulers.ui)
                .autoDisposable(scopeProvider)
                .subscribe {
                    tvUsername.setText(it.username, TextView.BufferType.EDITABLE)
                    tvPassword.setText(it.password, TextView.BufferType.EDITABLE)
                }

        mBtnSignIn.clicksThrottleFirst()
                .autoDisposable(scopeProvider)
                .subscribe { mViewModel.login(tvUsername.text.toString(), tvPassword.text.toString()) }
    }
}