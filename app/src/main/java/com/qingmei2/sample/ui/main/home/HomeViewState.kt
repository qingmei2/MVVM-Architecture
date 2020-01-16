package com.qingmei2.sample.ui.main.home

data class HomeViewState(
        val isLoading: Boolean,
        val throwable: Throwable?
) {

    companion object {

        fun initial(): HomeViewState {
            return HomeViewState(
                    isLoading = false,
                    throwable = null
            )
        }
    }
}