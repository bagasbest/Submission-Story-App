package com.project.dicodingplayground.practice_modul.androidexpert.modularization

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
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

        binding.fab.setOnClickListener {
            try {
                installChatModule()
            } catch (e: Exception) {
                Toast.makeText(this, "Module not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun installChatModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val moduleChat = "chat"
        if (splitInstallManager.installedModules.contains(moduleChat)) {
            moveToChatActivity()
            Toast.makeText(this, "Open Module", Toast.LENGTH_SHORT).show()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleChat)
                .build()
            
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(this, "Success installing module", Toast.LENGTH_SHORT).show()
                    moveToChatActivity()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error installing module", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun moveToChatActivity() {
        startActivity(Intent(this, Class.forName("com.project.chat.ChatActivity")))
    }

    private fun moveToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}