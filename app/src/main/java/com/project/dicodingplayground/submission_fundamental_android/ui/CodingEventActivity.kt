package com.project.dicodingplayground.submission_fundamental_android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.project.dicodingplayground.R
import com.project.dicodingplayground.databinding.ActivityCodingEventBinding

class CodingEventActivity : AppCompatActivity() {

    private var _binding : ActivityCodingEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCodingEventBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // This ties the BottomNavigationView with the NavController so that
        // selecting a menu item will navigate to the corresponding destination.
        binding.menu.setupWithNavController(navController)
    }
}