package com.qingmei2.sample.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseApplication
import com.qingmei2.sample.base.BaseFragment
import com.qingmei2.sample.databinding.FragmentMainBinding
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

@SuppressLint("CheckResult")
class MainFragment : BaseFragment<FragmentMainBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(BaseApplication.INSTANCE.kodein)
        import(mainKodeinModule)
        bind<MainFragment>() with instance(this@MainFragment)
    }

    override val layoutId: Int = R.layout.fragment_main

    private val delegate: MainViewDelegate by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.delegate = delegate
    }
}