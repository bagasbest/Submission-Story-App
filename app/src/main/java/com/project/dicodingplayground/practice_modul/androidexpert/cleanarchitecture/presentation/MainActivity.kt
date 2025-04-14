package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.dicodingplayground.databinding.ActivityMain14Binding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMain14Binding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       _binding = ActivityMain14Binding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.setName("Bagas Pangestu")
        viewModel.message.observe(this) {
            binding.tvWelcome.text = it.welcomeMessage
        }
    }
}