package com.example.mealmate.ui.home

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
import com.example.mealmate.databinding.FragmentHomeBinding
import okhttp3.Headers
import org.json.JSONException
private const val TAG = "FeedActivity/"
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var searchResultsAdapter: SearchResultsAdapter
    private var searchResultList = mutableListOf<SearchResult>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        //val recyclerView: RecyclerView =binding.recyclerView
        updateView("", "")
        val root: View = binding.root
        val cuisineOptions = arrayOf("","Italian", "Indian", "American", "French", "Chinese", "Japanese", "Thai")
        val sortByOptions = arrayOf("","meta-score", "popularity", "healthiness", "price", "random", "time", "protein")
        val spinnerCuisine: Spinner = binding.spinnerCuisine
        val spinnerSorting: Spinner = binding.spinnerSorting

        val arrayAdapter=ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, cuisineOptions)
        spinnerCuisine.adapter=arrayAdapter
        val sortAdapter=ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, sortByOptions)
        spinnerSorting.adapter=sortAdapter
        var cuisine=""
        var sortEntries=""
        spinnerSorting.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                sortEntries=sortByOptions[position]
                updateView(cuisine, sortEntries)
                //Toast.makeText(activity, "selected + " + cuisineOptions[position], Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                searchResultList.clear()
                searchResultsAdapter.notifyDataSetChanged()
                updateView("", "")
            }
        }
        spinnerCuisine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                cuisine=cuisineOptions[position]
                updateView(cuisine, sortEntries)
                //Toast.makeText(activity, "selected + " + cuisineOptions[position], Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                searchResultList.clear()
                searchResultsAdapter.notifyDataSetChanged()
                updateView("", "")
            }
        }
        return root
    }
    /*
    private fun updateView(searchText: String?) {
        val recyclerView: RecyclerView =binding.feedRecyclerView
        val results=searchRecipes(searchText)
        /*
        searchRecipes(searchText) { results ->
            searchResultList.clear()
            searchResultList.addAll(results)
            searchResultsAdapter.notifyDataSetChanged()
        }
         */
        searchResultsAdapter=SearchResultsAdapter(requireActivity(), results)
        recyclerView.adapter = searchResultsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity()).also {
            val dividerItemDecoration = DividerItemDecoration(requireActivity(), it.orientation)
            recyclerView.addItemDecoration(dividerItemDecoration)
        }
    }

     */
    private fun updateView(searchText: String?, sortByText: String?) {
        val recyclerView: RecyclerView = binding.feedRecyclerView
        searchRecipes(searchText, sortByText) { results ->
            searchResultList.clear()
            searchResultList.addAll(results)
            searchResultsAdapter.notifyDataSetChanged()
        }
        searchResultsAdapter = SearchResultsAdapter(requireActivity(), searchResultList)
        recyclerView.adapter = searchResultsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity()).also {
            val dividerItemDecoration = DividerItemDecoration(requireActivity(), it.orientation)
            recyclerView.addItemDecoration(dividerItemDecoration)
        }
    }
    private fun searchRecipes(searchText: String?, sortByText: String?, callback: (List<SearchResult>) -> Unit) {
        val client = AsyncHttpClient()

        var RECIPE_SEARCH_URL = "https://api.spoonacular.com/recipes/complexSearch?apiKey=${API_KEY}&cuisine=$searchText&sort=$sortByText"

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
                    val results = mutableListOf<SearchResult>()
                    parsedJson.result?.let { list ->
                        results.addAll(list)
                    }
                    callback(results)

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
    }

    /*
    private fun searchRecipes(searchText: String?): List<SearchResult> {
        val client = AsyncHttpClient()

        var RECIPE_SEARCH_URL = "https://api.spoonacular.com/recipes/complexSearch?apiKey=${API_KEY}&cuisine=$searchText"

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

     */

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}