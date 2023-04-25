package com.example.mealmate

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException
private const val TAG = "FavoriteActivity/"

class FavoriteActivity : AppCompatActivity() {
    private lateinit var favoriteRecipeAdapter: FavoriteRecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        // Get the recipeIds list from the intent extra
        val recipeIds = intent.getStringArrayListExtra("recipeIds") ?: arrayListOf()

        // TODO: Load the favorite recipes with the recipeIds list
        val favoriteRecipes = loadFavoriteRecipes(recipeIds)

        // Set up the RecyclerView and its adapter
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        favoriteRecipeAdapter = FavoriteRecipeAdapter(MainActivity.recipeIds)
        recyclerView.adapter = favoriteRecipeAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    // TODO: Implement the loadFavoriteRecipes function to load the favorite recipes with the recipeIds list
    private fun loadFavoriteRecipes(recipeIds: List<String>): List<FavoriteViewModel> {
        val favoriteRecipes = mutableListOf<FavoriteViewModel>()

        // For each recipeId in the list, fetch the recipe information using the Spoonacular API
        recipeIds.forEach { recipeId ->
            val client = AsyncHttpClient()
            val RECIPE_URL = "https://api.spoonacular.com/recipes/$recipeId/information?apiKey=${API_KEY}"
            val requestParams = RequestParams()
            requestParams.put("apiKey", API_KEY)

            client.get(RECIPE_URL, requestParams, object : JsonHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                    try {
                        // Parse the recipe information JSON and add the recipe to the list of favorite recipes
                        val recipe = createJson().decodeFromString(FavoriteViewModel.serializer(), json.jsonObject.toString())
                        favoriteRecipes.add(recipe)

                        // Notify the adapter that the data has changed
                        favoriteRecipeAdapter.notifyDataSetChanged()
                    } catch (e: JSONException) {
                        Log.e(TAG, "Exception: $e")
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String?,
                    throwable: Throwable?
                ) {
                    Log.e(TAG, "Failed to fetch recipe: $response")
                }
            })
        }

        return favoriteRecipes
    }
}
