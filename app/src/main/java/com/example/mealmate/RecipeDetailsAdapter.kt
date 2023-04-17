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


class RecipeDetailsAdapter(private val context: Context, private val recipeDetails: List<RecipeDetails>) :
    RecyclerView.Adapter<RecipeDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_recipe_instruction_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipeDetails[position])
    }


    override fun getItemCount() = recipeDetails.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var step: TextView
        private var task: TextView
        private var ingredients: TextView

        init {
            step = itemView.findViewById(R.id.instruction_stepNum)
            task = itemView.findViewById(R.id.instruction_task)
            ingredients=itemView.findViewById(R.id.ingredients_required)
        }

        fun bind(recipeDetails: RecipeDetails) {
            step.text = recipeDetails.stepNum
            task.text = recipeDetails.stepInfo
            ingredients.text= recipeDetails.ingredients.toString()
            // code to bind other search result properties to the view
        }

    }
}