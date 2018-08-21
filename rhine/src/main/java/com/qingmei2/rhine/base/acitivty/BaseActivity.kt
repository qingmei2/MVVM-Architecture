package com.qingmei2.rhine.base.acitivty

import android.app.ProgressDialog
import android.arch.lifecycle.LifecycleOwner
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.qingmei2.rhine.base.IView
import com.qingmei2.rhine.base.viewmodel.BaseRhineViewModel
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein

abstract class BaseActivity<B : ViewDataBinding, VM : BaseRhineViewModel> : AppCompatActivity(),
        KodeinAware, IView {

    private val parentKodein by closestKodein()

    override val kodein: Kodein by retainedKodein {
        extend(parentKodein, copy = Copy.All)
    }

    protected lateinit var binding: B

    protected lateinit var viewModel: VM

    protected var progressDialog: ProgressDialog? = null

    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDatabinding()
        initView()
        initData()
    }

    protected fun onStateChanged(state: BaseRhineViewModel.State) {
        when (state) {
            BaseRhineViewModel.State.LOAD_ING -> showLoading()
            BaseRhineViewModel.State.LOAD_WAIT,
            BaseRhineViewModel.State.LOAD_SUCCESS,
            BaseRhineViewModel.State.LOAD_FAILED -> hideLoading()
        }
    }

    override fun showLoading() {
        progressDialog = ProgressDialog(this)
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    override fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    override fun injectLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        viewModel.initLifecycleOwner(this)
    }

    protected fun initDatabinding() {
        binding = DataBindingUtil.setContentView(this, layoutId)
        viewModel = instanceViewModel()

        binding.setVariable(variableId(), instanceViewModel())
    }

    protected fun instanceViewModel(): VM = viewModel::class.java.newInstance()

    protected abstract fun variableId(): Int

    protected abstract fun initData()

    protected abstract fun initView()
}
