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

const val API_KEY = "258b2661d05f47a08e048ce42c190429"
private const val TAG = "SearchResultActivity/"
class SearchResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchResultBinding
    private lateinit var searchResultsAdapter: SearchResultsAdapter
    private lateinit var rvPopularMovie: RecyclerView
    private var searchResultList = mutableListOf<SearchResult>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchText = intent.getStringExtra("searchText")


        //val searchResults = searchText?.let { searchRecipes(it) }
        val searchResults=searchRecipes(searchText)

        //searchResultsAdapter = SearchResultsAdapter(this, searchResults)
        rvPopularMovie=findViewById<RecyclerView>(R.id.search_result_recycler_view)
        searchResultsAdapter=SearchResultsAdapter(this, searchResults)
        rvPopularMovie.adapter = searchResultsAdapter
        rvPopularMovie.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            rvPopularMovie.addItemDecoration(dividerItemDecoration)
        }
    }
    private fun searchRecipes(searchText: String?): List<SearchResult> {
        val client = AsyncHttpClient()

        var RECIPE_SEARCH_URL = "https://api.spoonacular.com/recipes/complexSearch?apiKey=${API_KEY}&query=$searchText"

        val requestParams = RequestParams()
        requestParams.put("query", searchText)
        requestParams.put("apiKey", API_KEY)



        client.get(RECIPE_SEARCH_URL, requestParams, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched articles: $json")
                try {
                    val parsedJson = createJson().decodeFromString(
                        SearchNewsResponse.serializer(),
                        json.jsonObject.toString()
                    )
                    parsedJson.result?.let { list ->
                        searchResultList.addAll(list)
                    }
                    searchResultsAdapter.notifyDataSetChanged()

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
                Log.e(TAG, "Failed to fetch articles: $response")
            }
        })

        return searchResultList
    }


}


