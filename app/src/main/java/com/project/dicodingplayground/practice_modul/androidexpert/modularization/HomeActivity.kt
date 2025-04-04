package com.project.dicodingplayground.practice_modul.androidexpert.modularization

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.core.SessionManager
import com.project.core.UserRepository
import com.project.dicodingplayground.databinding.ActivityHome5Binding

class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHome5Binding? = null
    private val binding get() = _binding!!
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHome5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val sesi = SessionManager(this)
        userRepository = UserRepository.getInstance(sesi)

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