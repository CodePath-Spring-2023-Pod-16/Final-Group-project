package com.example.mealmate

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.mealmate.MainActivity.Companion.recipeIds
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "FavoriteActivity/"

class FavoriteRecipeAdapter(private val recipes: MutableList<String>) :
    RecyclerView.Adapter<FavoriteRecipeAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeNameTextView: TextView = itemView.findViewById(R.id.recipeNameTv)
        val recipeImageView: ImageView = itemView.findViewById(R.id.recipeImageView)
        val prepTime: TextView = itemView.findViewById(R.id.prepTimeTV)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

        init {
            deleteButton.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Remove the recipe at the clicked position from the list
                    val removedRecipe = recipes.removeAt(position)
                    // Notify the adapter that the recipe has been removed from the list
                    notifyItemRemoved(position)
                    // TODO: Remove the recipe from the user's list of favorite recipes
                    MainActivity.recipeIds.removeAt(position)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val favRecipesView = inflater.inflate(R.layout.item_recipe, parent, false) //Inflate the custom layout
        return ViewHolder(favRecipesView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipeId = recipeIds[position]
        val recipeTitle = holder.recipeNameTextView
        val cookTime = holder.prepTime

        val client = AsyncHttpClient()

        val RECIPE_URL = "https://api.spoonacular.com/recipes/$recipeId/information?apiKey=${API_KEY}"
        val requestParams = RequestParams()
        requestParams.put("apiKey", API_KEY)
        client.get(RECIPE_URL, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched recipe: $json")
                try {
                    val recipe = createJson().decodeFromString(FavoriteViewModel.serializer(), json.jsonObject.toString())
                    recipeTitle.text = recipe.title
                    cookTime.text = recipe.prepTime.toString()

                    Glide.with(holder.itemView)
                        .load(recipe.recipeImageUrl)
                        .into(holder.recipeImageView)

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

    override fun getItemCount(): Int {
        return recipes.size
    }

}