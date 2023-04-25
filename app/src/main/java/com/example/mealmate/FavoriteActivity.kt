package com.example.mealmate

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.mealmate.*
import com.example.mealmate.databinding.FragmentFavoriteBinding
import com.example.mealmate.databinding.FragmentHomeBinding
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException
import org.json.JSONObject
import java.net.URL

private const val TAG = "FavoriteActivity/"
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var searchResultsAdapter: SearchResultsAdapter
    private var searchResultList = mutableListOf<String>()

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val favoriteModel =
            ViewModelProvider(this).get(FavoriteViewModel::class.java)

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        //val recyclerView: RecyclerView =binding.recyclerView

        val root: View = binding.root
    updateView()

        return root
    }

    private fun updateView() {
        val recyclerView: RecyclerView = binding.favoriteRecipesRecyclerView

        convertIDS(MainActivity.recipeIds) { searchResults ->
            searchResultsAdapter = SearchResultsAdapter(requireActivity(), searchResults)
            recyclerView.adapter = searchResultsAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireActivity()).also {
                val dividerItemDecoration = DividerItemDecoration(requireActivity(), it.orientation)
                recyclerView.addItemDecoration(dividerItemDecoration)
            }
        }

        //searchResultsAdapter.notifyDataSetChanged()
    }

    private fun convertIDS(ids: MutableList<String>?, callback: (List<SearchResult>) -> Unit) {
        val client = AsyncHttpClient()
        val searchResults = mutableListOf<SearchResult>()

        if (ids != null) {
            for (id in ids) {
                System.out.println(id)
                val url = "https://api.spoonacular.com/recipes/$id/information?apiKey=$API_KEY"
                val requestParams = RequestParams()
                requestParams.put("apiKey", API_KEY)

                    client.get(url, requestParams, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched recipes: $json")
                try {
                    val parsedJson = createJson().decodeFromString(
                        SearchNewsResponse.serializer(),
                        json.jsonObject.toString()
                    )

                    parsedJson.result?.let { list ->
                        for (resultJson in list) {
                            val searchResult = SearchResult(
                                id = resultJson.id.toString(),
                                title = resultJson.title.toString(),
                                poster = resultJson.poster.toString()
                            )
                            searchResults.add(searchResult)
                        }
                    }
                    callback(searchResults)

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
                        Log.e(TAG, "Failed to fetch recipe information for id $id")
                    }
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}