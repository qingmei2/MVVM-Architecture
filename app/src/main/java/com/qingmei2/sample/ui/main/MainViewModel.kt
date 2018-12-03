package com.qingmei2.sample.ui.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.qingmei2.rhine.base.viewmodel.BaseViewModel

class MainViewModel : BaseViewModel()

class MainViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            MainViewModel() as T
}