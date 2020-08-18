package com.qingmei2.sample.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest

class SearchViewModel @ViewModelInject constructor(
        private val repository: SearchRepository
) : BaseViewModel() {

    private val mMutableLiveData = MutableLiveData<String>("kotlin")

    fun search(keyWord: String?) {
        if (!keyWord.isNullOrEmpty()) {
            mMutableLiveData.postValue(keyWord)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val mFlow = mMutableLiveData.asFlow().flatMapLatest { repository.fetchPager(it).flow.cachedIn(viewModelScope) }

}

class SearchViewModelFactory(
        private val repository: SearchRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(repository) as T
    }

}