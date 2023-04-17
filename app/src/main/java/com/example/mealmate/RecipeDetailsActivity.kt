package com.example.mealmate

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.serialization.builtins.ListSerializer
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException


private const val TAG = "RecipeDetailsActivity/"
class RecipeDetailsActivity: AppCompatActivity() {
    private lateinit var recipeDetailsAdapter: RecipeDetailsAdapter
    private lateinit var rvPopularMovie: RecyclerView
    private var recipeInstructionList = mutableListOf<RecipeDetails>()
    private lateinit var recipeImageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)
        recipeImageView=findViewById(R.id.recipe_image)
        val id = intent.getStringExtra(RECIPE_ID)
        val image = intent.getStringExtra(RECIPE_IMAGE);
        val radius = 40;
        val margin = 10;
        Glide.with(this)
            .load(image)
            .centerInside()
            .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(radius,margin)))
            .into(recipeImageView)

        val searchResults=getRecipeDetails(id)

        //searchResultsAdapter = SearchResultsAdapter(this, searchResults)
        rvPopularMovie=findViewById<RecyclerView>(R.id.search_result_recycler_view)
        recipeDetailsAdapter=RecipeDetailsAdapter(this, searchResults)
        rvPopularMovie.adapter = recipeDetailsAdapter
        rvPopularMovie.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            rvPopularMovie.addItemDecoration(dividerItemDecoration)
        }
    }
    private fun getRecipeDetails(recipeID: String?): List<RecipeDetails> {
        val client = AsyncHttpClient()

        //var RECIPE_SEARCH_URL = "https://api.spoonacular.com/recipes/complexSearch?apiKey=${API_KEY}&query=$searchText"
        var RECIPE_INSTRUCTIONS_URL="https://api.spoonacular.com/recipes/${recipeID}/analyzedInstructions?apiKey=${API_KEY}"
        val requestParams = RequestParams()
        requestParams.put("query", recipeID)
        requestParams.put("apiKey", API_KEY)

        client.get(RECIPE_INSTRUCTIONS_URL, requestParams, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched articles: $json.jsonArray")
                try {
                    val jsonArray = json.jsonArray
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val stepsArray = jsonObject.getJSONArray("steps")
                        for (j in 0 until stepsArray.length()) {
                            val stepObject = stepsArray.getJSONObject(j)
                            val stepNumber = stepObject.getString("number")
                            val stepText = stepObject.getString("step")
                            val ingredientList = stepObject.getJSONArray("ingredients")
                            val ingredientNames = mutableListOf<String>()
                            for (k in 0 until ingredientList.length()) {
                                val ingredientObject = ingredientList.getJSONObject(k)
                                val ingredientName = ingredientObject.getString("name")
                                ingredientNames.add(ingredientName)
                            }
                            val recipeDetails = RecipeDetails(stepNumber, stepText, ingredientNames)
                            recipeInstructionList.add(recipeDetails)
                        }
                    }
                    recipeDetailsAdapter.notifyDataSetChanged()

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
                Log.e(TAG, "Failed to fetch articles: $response")
            }
        })
        return recipeInstructionList
    }
}


