package com.project.dicodingplayground.practice_modul.cleanarchitecture.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.dicodingplayground.R
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