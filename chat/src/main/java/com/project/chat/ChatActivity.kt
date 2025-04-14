package com.project.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.chat.databinding.ActivityChatBinding
import com.project.core.SessionManager
import com.project.core.UserRepository

class ChatActivity : AppCompatActivity() {

    private var _binding: ActivityChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sesi = SessionManager(this)
        val userRepository = UserRepository.getInstance(sesi)
        binding.tvChat.text = "Hello ${userRepository.getUser()}! \n Welcome to Chat Feature"
    }
}