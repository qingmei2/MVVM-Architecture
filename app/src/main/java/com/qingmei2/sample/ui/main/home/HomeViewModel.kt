package com.qingmei2.sample.ui.main.home

import androidx.fragment.app.Fragment
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel

@SuppressWarnings("checkResult")
class HomeViewModel @ViewModelInject constructor(
        private val repository: HomeRepository
) : BaseViewModel() {

    val mFlow = repository.fetchPager().flow.cachedIn(viewModelScope)

    companion object {

        fun instance(fragment: Fragment, repo: HomeRepository): HomeViewModel =
                ViewModelProvider(fragment, HomeViewModelFactory(repo)).get(HomeViewModel::class.java)
    }

}

class HomeViewModelFactory(private val repo: HomeRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repo) as T
    }
}
