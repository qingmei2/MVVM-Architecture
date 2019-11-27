package com.qingmei2.sample.ui.main.home

import androidx.paging.PagedList
import com.qingmei2.sample.entity.ReceivedEvent

data class HomeViewState(
        val isLoading: Boolean,
        val throwable: Throwable?,
        val pagedList: PagedList<ReceivedEvent>?
) {

    companion object {

        fun initial(): HomeViewState {
            return HomeViewState(
                    isLoading = false,
                    throwable = null,
                    pagedList = null
            )
        }
    }
}