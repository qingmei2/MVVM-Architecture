package com.qingmei2.sample.ui.main.repos

import androidx.annotation.MainThread
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel
import com.qingmei2.sample.entity.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@SuppressWarnings("checkResult")
class ReposViewModel @Inject constructor(
        private val repository: ReposRepository
) : BaseViewModel() {

    private val _viewStateLiveData: MutableLiveData<String> = MutableLiveData(sortByUpdate)

    val pagedListLiveData: LiveData<PagingData<Repo>>
        get() = repository.fetchRepoPager().flow.cachedIn(viewModelScope).asLiveData()

    init {
        repository.sortKeyProvider = ::fetchSortKey
    }

    @MainThread
    fun setSortKey(sort: String): Boolean {
        return if (sort != fetchSortKey()) {
            _viewStateLiveData.postValue(sort)
            true
        } else {
            false
        }
    }

    @MainThread
    fun fetchSortKey(): String {
        return _viewStateLiveData.value!!
    }

    companion object {

        const val sortByCreated: String = "created"

        const val sortByUpdate: String = "updated"      // default

        const val sortByLetter: String = "full_name"
    }
}
