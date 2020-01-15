package com.qingmei2.sample.ui.login

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.qingmei2.architecture.core.base.view.fragment.BaseFragment
import com.qingmei2.architecture.core.ext.observe
import com.qingmei2.sample.R
import com.qingmei2.sample.http.Errors
import com.qingmei2.sample.ui.MainActivity
import com.qingmei2.sample.utils.toast
import kotlinx.android.synthetic.main.fragment_login.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import retrofit2.HttpException

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
        mBtnSignIn.setOnClickListener {
            mViewModel.login(tvUsername.text.toString(), tvPassword.text.toString())
        }

        observe(mViewModel.stateLiveData, this::onNewState)
    }

    private fun onNewState(state: LoginViewState) {
        if (state.throwable != null) {
            when (state.throwable) {
                is Errors.EmptyInputError -> "username or password can't be null."
                is HttpException ->
                    when (state.throwable.code()) {
                        401 -> "username or password failure."
                        else -> "network failure"
                    }
                else -> "network failure"
            }.also { str ->
                toast { str }
            }
        }

        mProgressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        if (state.autoLoginEvent != null                // has auto login info
                && state.autoLoginEvent.autoLogin       // allow auto login by user
                && state.useAutoLoginEvent              // ensure auto login info be used one time
        ) {
            tvUsername.setText(state.autoLoginEvent.username, TextView.BufferType.EDITABLE)
            tvPassword.setText(state.autoLoginEvent.password, TextView.BufferType.EDITABLE)

            mViewModel.onAutoLoginEventUsed()
            mViewModel.login(state.autoLoginEvent.username, state.autoLoginEvent.password)
        }

        if (state.loginInfo != null) {
            MainActivity.launch(activity!!)
        }
    }
}