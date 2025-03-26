package com.project.dicodingplayground.practice_modul.transition

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.project.dicodingplayground.databinding.ActivityHeroBinding
import com.project.dicodingplayground.practice_modul.transition.model.Hero

class HeroActivity : AppCompatActivity() {

    private var _binding : ActivityHeroBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupData()
    }

    private fun setupData() {
        val hero = intent.getParcelableExtra<Hero>("Hero") as Hero
        Glide.with(applicationContext)
            .load(hero.photo)
            .circleCrop()
            .into(binding.profileImageView)
        binding.nameTextView.text = hero.name
        binding.descTextView.text = hero.description
    }
}