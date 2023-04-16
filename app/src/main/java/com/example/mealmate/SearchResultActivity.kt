package com.example.mealmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException
import kotlinx.serialization.json.Json
import com.example.mealmate.databinding.ActivitySearchResultBinding

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

const val API_KEY = "3315e5304bb34417bd8b8f00d0b92dd8"
private const val TAG = "SearchResultActivity/"
class SearchResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchResultBinding
    private lateinit var searchResultsAdapter: SearchResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchText = intent.getStringExtra("searchText")


        val searchResults = searchText?.let { searchRecipes(it) }

        //searchResultsAdapter = SearchResultsAdapter(searchResults)
        searchResultsAdapter = searchResults?.let { SearchResultsAdapter(it) }!!
        binding.searchResultsRecyclerView.adapter = searchResultsAdapter
        binding.searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
    }
    private fun searchRecipes(searchText: String): List<SearchResult> {
        val client = AsyncHttpClient()

        var RECIPE_SEARCH_URL = "https://api.spoonacular.com/recipes/complexSearch?apiKey=${API_KEY}&query=$searchText"

        val requestParams = RequestParams()
        requestParams.put("query", searchText)
        requestParams.put("apiKey", API_KEY)

        val searchResultList = mutableListOf<SearchResult>()

        client.get(RECIPE_SEARCH_URL, requestParams, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched articles: $json")
                try {
                    val parsedJson = createJson().decodeFromString(
                        SearchNewsResponse.serializer(),
                        json.jsonObject.toString()
                    )
                    parsedJson.result?.let { list ->
                        popularMovieList.addAll(list)
                    }
                    searchResultsAdapter.notifyDataSetChanged()

                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
                /*
                val searchResultsJsonArray = json?.getJSONObject("results")?.getJSONArray("searchResults")
                val searchResults = Gson().fromJson(searchResultsJsonArray.toString(), Array<SearchResult>::class.java)
                searchResultList.addAll(searchResults)

                 */


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

        return searchResultList
    }


}