package com.qingmei2.sample.ui.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.qingmei2.rhine.base.viewmodel.BaseViewModel

class MainViewModel : BaseViewModel() {

    companion object {
        fun instance(fragment: Fragment): MainViewModel =
                ViewModelProviders
                        .of(fragment, MainViewModelFactory)
                        .get(MainViewModel::class.java)
    }
}

@Suppress("UNCHECKED_CAST")
object MainViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            MainViewModel() as T
}