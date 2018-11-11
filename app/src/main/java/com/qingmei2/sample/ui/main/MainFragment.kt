package com.qingmei2.sample.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseFragment
import com.qingmei2.sample.databinding.FragmentMainBinding
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

@SuppressLint("CheckResult")
class MainFragment : BaseFragment<FragmentMainBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(mainKodeinModule)
        bind<FragmentManager>() with provider {
            childFragmentManager
        }
    }

    override val layoutId: Int = R.layout.fragment_main

    private val delegate: MainViewDelegate by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.delegate = delegate
    }
}