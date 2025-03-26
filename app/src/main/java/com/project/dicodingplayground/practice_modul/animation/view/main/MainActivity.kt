package com.project.dicodingplayground.practice_modul.animation.view.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.project.dicodingplayground.databinding.ActivityMain2Binding
import com.project.dicodingplayground.practice_modul.animation.view.ViewModelFactory
import com.project.dicodingplayground.practice_modul.animation.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMain2Binding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().asLiveData().observe(this) { user ->
            if (user.isLogin) {
                binding.nameTextView.text = "Halo, ${user.name}"
            } else {
                startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))
            }
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}