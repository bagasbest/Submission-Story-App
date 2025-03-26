package com.project.dicodingplayground.practice_modul.animation.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.project.dicodingplayground.databinding.ActivityLoginBinding
import com.project.dicodingplayground.practice_modul.animation.view.ViewModelFactory
import com.project.dicodingplayground.practice_modul.animation.view.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        playAnimation()
    }

    private fun setupView() {
        binding.apply {
            loginButton.setOnClickListener {
                val email = emailEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()

                if (email.isEmpty()) {
                    Toast.makeText(this@LoginActivity, "Email must be filled!", Toast.LENGTH_SHORT).show()
                } else if (password.length < 6) {
                    Toast.makeText(this@LoginActivity, "Password minimum 6 character!", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.checkSession().asLiveData().observe(this@LoginActivity) {
                        val emailCheck = it.email
                        val passwordCheck = it.password

                        if (email == emailCheck && password == passwordCheck) {
                            viewModel.setLogin()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Email or Password is wrong!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -50f, 50f).apply {
            duration = 2000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()


        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f)
        val message = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f)
        val email = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f)
        val emailLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f)
        val password = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f)
        val passwordLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f)

        val together = AnimatorSet().apply {
            playTogether(email, emailLayout)
            start()
        }

        val together2 = AnimatorSet().apply {
            playTogether(password, passwordLayout)
            start()
        }

        AnimatorSet().apply {
            playSequentially(title, message, together, together2, login)
            start()
        }
    }
}