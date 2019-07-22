package com.qingmei2.sample.ui.main.repos

import androidx.paging.PagedList
import com.qingmei2.sample.entity.Repo

data class ReposViewState(
        val isLoading: Boolean,
        val throwable: Throwable?,
        val pagedList: PagedList<Repo>?,
        val nextPageData: List<Repo>?,  // useless in this sample, but it's useful sometimes.
        val sort: String
) {

    companion object {

        fun initial(): ReposViewState {
            return ReposViewState(
                    isLoading = false,
                    throwable = null,
                    pagedList = null,
                    nextPageData = null,
                    sort = ReposViewModel.sortByUpdate
            )
        }
    }
}