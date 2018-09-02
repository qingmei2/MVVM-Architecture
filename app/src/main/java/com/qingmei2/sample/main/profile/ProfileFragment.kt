package com.qingmei2.sample.main.profile

import android.os.Bundle
import android.view.View
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseFragment
import com.qingmei2.sample.databinding.FragmentProfileBinding
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(profileKodeinModule)
    }

    private val viewDelegate: ProfileViewDelegate by instance()

    override val layoutId: Int = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.delegate = viewDelegate
    }
}