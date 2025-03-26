package com.project.dicodingplayground.practice_modul.paging3.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.dicodingplayground.databinding.ActivityMain12Binding
import com.project.dicodingplayground.practice_modul.paging3.adapter.LoadingStateAdapter
import com.project.dicodingplayground.practice_modul.paging3.adapter.QuoteListAdapter

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMain12Binding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMain12Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvQuote.layoutManager = LinearLayoutManager(this)

        getData()
    }

    private fun getData() {
        val adapter = QuoteListAdapter()
        binding.rvQuote.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
       mainViewModel.quote.observe(this) {
           adapter.submitData(lifecycle, it)
       }
    }
}