package com.project.dicodingplayground.practice_modul.androidexpert.koin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.dicodingplayground.databinding.ActivityHome3Binding
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHome3Binding? = null
    private val binding get() = _binding!!
    private val userRepository: UserRepository by inject()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHome3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvWelcome.text = "Welcome ${userRepository.getUser()}"

        binding.btnLogout.setOnClickListener {
            userRepository.logoutUser()
            moveToMainActivity()
        }
    }

    private fun moveToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}