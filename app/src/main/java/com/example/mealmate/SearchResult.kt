package com.example.mealmate

import android.app.appsearch.SearchResult
import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class SearchNewsResponse(
    @SerialName("results")
    val result: List<SearchResult>?
)
@Keep
@Serializable
data class SearchResult (
    @SerialName("id")
    val id: String?,
    @SerialName("title")
    val title: String?,
) : java.io.Serializable
