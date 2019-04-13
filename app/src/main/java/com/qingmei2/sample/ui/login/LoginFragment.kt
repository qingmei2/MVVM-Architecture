package com.qingmei2.sample.ui.login

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.jakewharton.rxbinding3.widget.textChanges
import com.qingmei2.rhine.base.view.fragment.BaseFragment
import com.qingmei2.rhine.ext.livedata.map
import com.qingmei2.rhine.ext.livedata.toReactiveStream
import com.qingmei2.rhine.ext.reactivex.clicksThrottleFirst
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

    val mViewModel: LoginViewModel by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binds()
    }

    private fun binds() {
        mViewModel.loginIndicatorVisible
                .map { if (it) View.VISIBLE else View.GONE }
                .toReactiveStream()
                .autoDisposable(scopeProvider)
                .subscribe { mProgressBar.visibility = it }
        mViewModel.username.toReactiveStream()
                .distinctUntilChanged()
                .autoDisposable(scopeProvider)
                .subscribe { tvUsername.setText(it, TextView.BufferType.EDITABLE) }
        mViewModel.password.toReactiveStream()
                .distinctUntilChanged()
                .autoDisposable(scopeProvider)
                .subscribe { tvPassword.setText(it, TextView.BufferType.EDITABLE) }

        mViewModel.userInfo
                .toReactiveStream()
                .autoDisposable(scopeProvider)
                .subscribe { MainActivity.launch(activity!!) }

        mBtnSignIn.clicksThrottleFirst()
                .autoDisposable(scopeProvider)
                .subscribe { mViewModel.login() }

        tvUsername.textChanges()
                .autoDisposable(scopeProvider)
                .subscribe { mViewModel.username.postValue(it.toString()) }
        tvPassword.textChanges()
                .autoDisposable(scopeProvider)
                .subscribe { mViewModel.password.postValue(it.toString()) }
    }
}