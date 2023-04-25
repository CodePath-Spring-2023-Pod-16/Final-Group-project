package com.example.mealmate

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


const val RECIPE_ID = "RECIPE_ID"
const val RECIPE_IMAGE = "RECIPE_IMAGE"
class SearchResultsAdapter(private val context: Context, private val searchResults: List<SearchResult>) :
    RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_search_result_recipelist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(searchResults[position])
    }

    override fun getItemCount() = searchResults.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var id: TextView
        private var name: TextView
        private var poster: ImageView
        private var addButton: Button
        init {
            itemView.setOnClickListener(this)
            id = itemView.findViewById(R.id.recipe_id)
            name = itemView.findViewById(R.id.recipe_name)
            poster=itemView.findViewById(R.id.recipe_image)

            addButton = itemView.findViewById(R.id.button)
            addButton.setOnClickListener {
                val recipeId = id.text.toString()
                MainActivity.recipeIds.add(recipeId)
                Log.i("Recipe ID added", "Recipe ID $recipeId added to recipeIds: ${MainActivity.recipeIds}")
            }
        }

        fun bind(searchResult: SearchResult) {
            name.text = searchResult.title
            id.text = searchResult.id
            val radius = 40; // corner radius, higher value = more rounded
            val margin = 10;
            Glide.with(itemView)
                .load(searchResult.poster)
                .centerInside()
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(radius,margin)))
                .into(poster)
            // code to bind other search result properties to the view
        }
        override fun onClick(p0: View?) {
            val recipe = searchResults[absoluteAdapterPosition]
            val intent = Intent(context, RecipeDetailsActivity::class.java)
            Log.i(recipe.id, "Successfully fetched articles: $recipe.id")
            intent.putExtra(RECIPE_ID, recipe.id)
            intent.putExtra(RECIPE_IMAGE, recipe.poster)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, (poster as View?)!!, "poster")
            context.startActivity(intent, options.toBundle())
        }
    }
}