package com.project.dicodingplayground.practice_modul.motionlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.dicodingplayground.databinding.ActivityMain4Binding

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMain4Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}