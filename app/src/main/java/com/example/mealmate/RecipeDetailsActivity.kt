package com.example.mealmate

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import kotlinx.serialization.json.Json



class RecipeDetailsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)
        val id = intent.getStringExtra(RECIPE_ID)
        val image = intent.getStringExtra(RECIPE_IMAGE);
    }
}