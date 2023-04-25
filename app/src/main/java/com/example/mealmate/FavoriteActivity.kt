package com.example.mealmate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
        // TODO: Implement this function to load the favorite recipes
        return listOf()
    }
}
