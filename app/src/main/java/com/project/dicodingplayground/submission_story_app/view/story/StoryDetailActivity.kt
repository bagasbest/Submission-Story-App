package com.project.dicodingplayground.submission_story_app.view.story

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.project.dicodingplayground.R
import com.project.dicodingplayground.databinding.ActivityStoryDetailBinding
import com.project.dicodingplayground.submission_story_app.data.api.response.ListStoryItem

class StoryDetailActivity : AppCompatActivity() {

    private var _binding : ActivityStoryDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupData()
    }

    private fun setupData() {
        val story = intent.getParcelableExtra<ListStoryItem>(EXTRA_DATA)
        Glide.with(this)
            .load(story?.photoUrl)
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_error)
            .into(binding.profileImageView)
        binding.nameTextView.text = story?.name
        binding.descTextView.text = story?.description
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_DATA = "data"
    }
}