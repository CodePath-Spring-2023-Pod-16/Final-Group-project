package com.example.mealmate
import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class FavoriteViewModel(
    @SerialName("id")
    val recipeId: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("image")
    val recipeImageUrl: String?,
    @SerialName("maxReadyTime")
    val prepTime: Int
) : java.io.Serializable

@Keep
@Serializable
data class FavoriteResponse(
    @SerialName("results")
    val result: List<SearchResult>?
)