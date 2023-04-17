package com.example.mealmate.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealmate.R

class FavoriteRecipeAdapter(private val recipes: MutableList<FavoriteRecipeModel>) :
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
        val pos = recipes[position] // Get the data model based on position
        val recipeTitle = holder.recipeNameTextView
        recipeTitle.text = pos.title
        val cookTime = holder.prepTime
        cookTime.text = pos.prepTime.toString()
        Glide.with(holder.itemView)
            .load(pos.recipeImageUrl)
            .into(holder.recipeImageView)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

}