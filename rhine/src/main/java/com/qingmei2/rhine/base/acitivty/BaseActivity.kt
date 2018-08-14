package com.qingmei2.rhine.base.acitivty

import android.app.ProgressDialog
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.qingmei2.rhine.base.BaseViewModel

import javax.inject.Inject

import dagger.android.AndroidInjection

abstract class BaseActivity<B : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    protected var binding: B

    @Inject
    var viewModel: V? = null

    protected var progressDialog: ProgressDialog? = null

    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        injectIfNeeded()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        initView()
        initData()
    }

    private fun injectIfNeeded() {
        try {
            AndroidInjection.inject(this)
        } catch (ignored: IllegalArgumentException) {
        }

    }

    protected fun onStateChanged(state: BaseViewModel.State) {
        when (state) {
            BaseViewModel.State.LOAD_WAIT -> {
            }
            BaseViewModel.State.LOAD_ING -> loading(true)
            BaseViewModel.State.LOAD_SUCCESS -> loading(false)
            BaseViewModel.State.LOAD_FAILED -> loading(false)
            else -> {
            }
        }
    }

    protected fun loading(showing: Boolean) {
        if (showing) {
            progressDialog = ProgressDialog(this)
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        } else if (progressDialog != null) {
            progressDialog!!.dismiss()
            progressDialog = null
        }
    }

    protected abstract fun initData()

    protected abstract fun initView()

}
