package com.example.mealmate

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation



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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var id: TextView
        private var name: TextView
        private var poster: ImageView

        init {
            id = itemView.findViewById(R.id.recipe_id)
            name = itemView.findViewById(R.id.recipe_name)
            poster=itemView.findViewById(R.id.recipe_image)
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
    }
}