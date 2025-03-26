package com.project.dicodingplayground.practice_modul.animation.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.dicodingplayground.databinding.ActivitySignupBinding
import com.project.dicodingplayground.practice_modul.animation.data.pref.UserModel
import com.project.dicodingplayground.practice_modul.animation.view.ViewModelFactory
import com.project.dicodingplayground.practice_modul.animation.view.login.LoginViewModel
import com.project.dicodingplayground.practice_modul.animation.view.welcome.WelcomeActivity

class SignupActivity : AppCompatActivity() {

    private var _binding : ActivitySignupBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAnimation()
    }

    private fun setupAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f)
        val name = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f)
        val nameLayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f)
        val email = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f)
        val emailLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f)
        val password = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f)
        val passwordLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f)
        val register = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f)

        val together0 = AnimatorSet().apply {
            playTogether(name, nameLayout)
            start()
        }

        val together = AnimatorSet().apply {
            playTogether(email, emailLayout)
            start()
        }

        val together2 = AnimatorSet().apply {
            playTogether(password, passwordLayout)
            start()
        }


        AnimatorSet().apply {
            playSequentially(title, together0, together, together2, register)
            start()
        }
    }

    private fun setupView() {
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (name.isEmpty()) {
                binding.nameEditTextLayout.error = "Name is required"
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                binding.emailEditTextLayout.error = "Email is required"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.passwordEditTextLayout.error = "Password is required"
                return@setOnClickListener
            }

            if (password.length < 6) {
                binding.passwordEditTextLayout.error = "Password must be at least 6 characters"
                return@setOnClickListener
            }

            val user = UserModel(email, "123456789", name, password, false)
            viewModel.saveSession(user)
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }


}