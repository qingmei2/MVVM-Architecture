package com.qingmei2.sample.main.task

import com.qingmei2.sample.R
import com.qingmei2.sample.base.BaseFragment
import com.qingmei2.sample.databinding.FragmentTaskBinding

class TaskFragment : BaseFragment<FragmentTaskBinding, TaskViewDelegate>() {

    override val delegateSupplier = {
        TaskViewDelegate().apply {
            binding.delegate = this@apply
        }
    }

    override val layoutId: Int = R.layout.fragment_task
}