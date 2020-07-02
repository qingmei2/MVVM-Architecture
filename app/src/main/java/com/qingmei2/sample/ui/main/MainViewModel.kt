package com.qingmei2.sample.ui.main

import androidx.fragment.app.Fragment
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel

class MainViewModel @ViewModelInject constructor() : BaseViewModel() {

    companion object {
        fun instance(fragment: Fragment): MainViewModel =
                ViewModelProvider(fragment, MainViewModelFactory).get(MainViewModel::class.java)
    }
}

@Suppress("UNCHECKED_CAST")
object MainViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel() as T
}
