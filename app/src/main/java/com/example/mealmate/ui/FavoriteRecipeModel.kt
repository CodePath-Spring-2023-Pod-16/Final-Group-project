package com.example.mealmate.ui
import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class FavoriteRecipeModel(
    @SerialName("title")
    val title: String?,
    @SerialName("image")
    val recipeImageUrl: String?,
    @SerialName("maxReadyTime")
    val prepTime: Int
) : java.io.Serializable
