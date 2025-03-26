package com.project.dicodingplayground.submission_story_app.view.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.dicodingplayground.databinding.ActivityLogin2Binding
import com.project.dicodingplayground.submission_story_app.data.Result
import com.project.dicodingplayground.submission_story_app.data.api.payload.LoginPayload
import com.project.dicodingplayground.submission_story_app.data.api.response.LoginResult
import com.project.dicodingplayground.submission_story_app.data.pref.UserModel
import com.project.dicodingplayground.submission_story_app.view.MainViewModel
import com.project.dicodingplayground.submission_story_app.view.ViewModelFactory
import com.project.dicodingplayground.submission_story_app.view.story.StoryActivity

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLogin2Binding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
        setMyButton()


        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {}

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {}

            override fun afterTextChanged(p0: Editable?) {
                setMyButton()
            }
        })

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {}

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {}

            override fun afterTextChanged(p0: Editable?) {
                setMyButton()
            }
        })
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
        }

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val description = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
        val tvEmail = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val etEmail = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val tvPassword = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val etPassword = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)

        val playTogetherEmail = AnimatorSet().apply {
            playTogether(tvEmail, etEmail)
        }

        val playTogetherPassword = AnimatorSet().apply {
            playTogether(tvPassword, etPassword)
        }

        AnimatorSet().apply {
            playSequentially(title, description, playTogetherEmail, playTogetherPassword, btnLogin)
            start()
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val payload = LoginPayload(email, password)
            viewModel.login(payload).observe(this) { event ->
                when(event) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        saveSessionToDataStore(event.data.loginResult)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, event.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun saveSessionToDataStore(loginResult: LoginResult?) {
        val userModel = UserModel(
            userId = loginResult?.userId!!,
            name = loginResult.name!!,
            token = loginResult.token!!
        )
        viewModel.saveSession(userModel)
        startActivity(Intent(this, StoryActivity::class.java))
        finish()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setMyButton() {
        binding.loginButton.isEnabled = binding.emailEditText.isValid && binding.passwordEditText.isValid
    }
}