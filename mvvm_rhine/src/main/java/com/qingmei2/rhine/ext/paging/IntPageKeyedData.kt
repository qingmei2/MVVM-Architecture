package com.qingmei2.rhine.ext.paging

data class IntPageKeyedData<T>(
        val data: List<T>,
        val pageIndex: Int,
        val hasAdjacentPageKey: Boolean
) {

    companion object {

        fun <T> build(
                data: List<T>,
                pageIndex: Int,
                hasAdjacentPageKey: Boolean = true
        ): IntPageKeyedData<T> = IntPageKeyedData(
                data = data,
                pageIndex = pageIndex,
                hasAdjacentPageKey = hasAdjacentPageKey
        )

        fun <T> empty(): IntPageKeyedData<T> = IntPageKeyedData(
                data = emptyList(),
                pageIndex = -1,
                hasAdjacentPageKey = false
        )
    }
}