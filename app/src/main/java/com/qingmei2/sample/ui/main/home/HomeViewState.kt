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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HomeViewState

        if (isLoading != other.isLoading) return false
        if (throwable != other.throwable) return false
        if (pagedList != other.pagedList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isLoading.hashCode()
        result = 31 * result + (throwable?.hashCode() ?: 0)
        result = 31 * result + (pagedList?.hashCode() ?: 0)
        return result
    }
}