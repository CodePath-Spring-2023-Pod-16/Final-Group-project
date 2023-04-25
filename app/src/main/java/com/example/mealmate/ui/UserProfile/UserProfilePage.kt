package com.example.mealmate.ui.UserProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealmate.R
//import com.example.mealmate.FavoriteRecipeAdapter
import com.example.mealmate.FavoriteViewModel


class UserProfilePage : Fragment() {
    private var userName: String? = "Marry Wells" // TODO get the user name from the login page
    private var favoriteRecipes: List<FavoriteViewModel>? = null // TODO Variable to store the user's list of favorite recipes, get the recipes from HOME feeds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Retrieve the user's list of favorite recipes from the database or from whatever we storing them
    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_user_profile_page, container, false)  // Inflate the layout for this fragment
//        val userNameTextView = view.findViewById<TextView>(R.id.userNameTextView)
//        userNameTextView.text = userName // set the user name to whatever name provide and display it
//        val recyclerView = view.findViewById<RecyclerView>(R.id.favoriteRecipesRecyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(activity)
//        recyclerView.adapter = favoriteRecipes?.let { FavoriteRecipeAdapter(it.toMutableList()) } // Set the adapter to the RecyclerView // TODO get a list of favorites and put in the list and display in the recycle review
//        return view
//    }

    override fun onDestroy() {
        super.onDestroy()
        // TODO: Clean up any resources or listeners set up in onCreate()
    }


    companion object {
        fun newInstance(): UserProfilePage {
            return UserProfilePage()
        }
    }
}