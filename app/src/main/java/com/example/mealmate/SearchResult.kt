package com.example.mealmate


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
    @SerialName("image")
    val poster: String?
) : java.io.Serializable
