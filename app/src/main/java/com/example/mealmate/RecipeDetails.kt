package com.example.mealmate

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class RecipeDetailsResponse(
    @SerialName("steps")
    val result: List<SearchResult>?
)
@Keep
@Serializable
data class RecipeDetails (
    @SerialName("number")
    val stepNum: String?,
    @SerialName("step")
    val stepInfo: String?,
    @SerialName("ingredients")
    val ingredients: List<Ingredients>?
) : java.io.Serializable

@Keep
@Serializable
data class Ingredients (
    @SerialName("name")
    val ingredientName: String?,
) : java.io.Serializable

