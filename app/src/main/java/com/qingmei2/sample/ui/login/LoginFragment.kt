package com.qingmei2.sample.ui.login

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.qingmei2.architecture.core.base.view.fragment.BaseFragment
import com.qingmei2.architecture.core.ext.observe
import com.qingmei2.sample.R
import com.qingmei2.sample.http.Errors
import com.qingmei2.sample.ui.MainActivity
import com.qingmei2.sample.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.HttpException

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_login

    private val mViewModel: LoginViewModel by viewModels()

    private val permissionRequest: ActivityResultLauncher<String> =
            prepareCall(ActivityResultContracts.RequestPermission()) { isGrant ->
                when (isGrant) {
                    true -> mViewModel.login(tvUsername.text.toString(), tvPassword.text.toString())
                    false -> Toast.makeText(requireContext(), "need permission first.", Toast.LENGTH_SHORT).show()
                }
            }

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
            MainActivity.launch(requireActivity())
        }
    }
}
