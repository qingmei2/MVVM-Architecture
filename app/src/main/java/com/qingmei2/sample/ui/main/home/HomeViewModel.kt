package com.qingmei2.sample.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.qingmei2.architecture.core.base.viewmodel.BaseViewModel
import com.qingmei2.sample.entity.ReceivedEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@SuppressWarnings("checkResult")
class HomeViewModel @Inject constructor(
        repository: HomeRepository
) : BaseViewModel() {

    val eventListLiveData: LiveData<PagingData<ReceivedEvent>> =
            repository.fetchPager().flow.cachedIn(viewModelScope).asLiveData()
}
