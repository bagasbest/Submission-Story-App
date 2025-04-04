package com.project.dicodingplayground.practice_modul.androidexpert.dagger

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.dicodingplayground.MyApplication
import com.project.dicodingplayground.databinding.ActivityMain19Binding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMain19Binding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityMain19Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if (userRepository.isUserLogin()) {
            moveToHomeActivity()
        }

        binding.btnLogin.setOnClickListener {
            saveSession()
        }
    }

    private fun saveSession() {
        userRepository.loginUser(binding.edUsername.text.toString())
        moveToHomeActivity()
    }

    private fun moveToHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}