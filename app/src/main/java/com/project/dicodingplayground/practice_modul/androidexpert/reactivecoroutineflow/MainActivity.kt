package com.project.dicodingplayground.practice_modul.androidexpert.reactivecoroutineflow

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.project.dicodingplayground.R
import com.project.dicodingplayground.databinding.ActivityMain17Binding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMain17Binding? = null
    private val binding get() = _binding!!
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMain17Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edPlace.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               lifecycleScope.launch {
                   viewModel.queryChannel.value = p0.toString()
               }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        viewModel.searchResult.observe(this) { placesItem ->
            val placesName = placesItem.map { it.placeName }
            val adapter = ArrayAdapter(this@MainActivity, android.R.layout.select_dialog_item, placesName)
            adapter.notifyDataSetChanged()
            binding.edPlace.setAdapter(adapter)
        }
    }
}