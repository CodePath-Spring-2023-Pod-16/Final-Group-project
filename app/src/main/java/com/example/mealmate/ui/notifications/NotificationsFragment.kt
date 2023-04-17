package com.example.mealmate.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.mealmate.SearchResultActivity
import com.example.mealmate.databinding.FragmentSearchBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private lateinit var searchButton: Button
    private lateinit var searchEditText: EditText

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        searchButton = binding.searchButton
        searchEditText = binding.editText

        searchButton.setOnClickListener {
            val searchText = searchEditText.text.toString()
            val intent = Intent(context, SearchResultActivity::class.java)
            intent.putExtra("searchText", searchText)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
