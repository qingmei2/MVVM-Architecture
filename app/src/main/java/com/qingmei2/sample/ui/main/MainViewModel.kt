package com.qingmei2.sample.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.qingmei2.rhine.base.viewmodel.BaseViewModel

class MainViewModel : BaseViewModel()

class MainViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            MainViewModel() as T
}