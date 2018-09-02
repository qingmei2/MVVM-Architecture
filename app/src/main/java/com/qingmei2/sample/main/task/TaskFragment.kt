package com.qingmei2.sample.main.task

import android.os.Bundle
import android.view.View
import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseFragment
import com.qingmei2.sample.databinding.FragmentTaskBinding
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class TaskFragment : BaseFragment<FragmentTaskBinding>() {

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(taskKodeinModule)
    }

    private val viewDelegate: TaskViewDelegate by instance()

    override val layoutId: Int = R.layout.fragment_task

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.delegate = viewDelegate
    }
}