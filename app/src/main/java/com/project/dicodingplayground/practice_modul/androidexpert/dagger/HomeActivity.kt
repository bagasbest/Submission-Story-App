package com.project.dicodingplayground.practice_modul.androidexpert.dagger

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.dicodingplayground.MyApplication
import com.project.dicodingplayground.R
import com.project.dicodingplayground.databinding.ActivityHome4Binding
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHome4Binding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityHome4Binding.inflate(layoutInflater)
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