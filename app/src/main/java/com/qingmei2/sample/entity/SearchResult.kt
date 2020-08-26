package com.qingmei2.sample.entity

import com.google.gson.annotations.SerializedName

data class SearchResult(
        @SerializedName("incomplete_results")
        val incompleteResults: Boolean,
        @SerializedName("items")
        val items: List<Repo>,
        @SerializedName("total_count")
        val totalCount: Int
)
