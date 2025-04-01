package com.project.dicodingplayground.submission_story_app.view.story

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.dicodingplayground.R
import com.project.dicodingplayground.databinding.ActivityStoryBinding
import com.project.dicodingplayground.practice_modul.paging3.adapter.LoadingStateAdapter
import com.project.dicodingplayground.submission_story_app.view.MainViewModel
import com.project.dicodingplayground.submission_story_app.view.ViewModelFactory
import com.project.dicodingplayground.submission_story_app.view.auth.LoginActivity

class StoryActivity : AppCompatActivity() {

    private var _binding : ActivityStoryBinding? = null
    private val binding get() = _binding!!
    private val storyAdapter = StoryAdapter()
    private lateinit var name: String
    private val viewModelMain: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private val viewModel: StoryViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getStories()
        initViewModel()
        initView()
    }

    private fun initView() {
        binding.apply {
            rvStory.layoutManager = LinearLayoutManager(this@StoryActivity)
            btnLogout.setOnClickListener {
                confirmLogout()
            }
            btnAddStory.setOnClickListener {
                val intent = Intent(this@StoryActivity, StoryAddActivity::class.java)
                storyAddLauncher.launch(intent)
            }
            btnLocalization.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            btnMaps.setOnClickListener {
                val intent = Intent(this@StoryActivity, MapsActivity::class.java)
                intent.putExtra(MapsActivity.EXTRA_NAME, name)
                startActivity(intent)
            }
        }
    }

    private fun initViewModel() {
        viewModelMain.getSession().asLiveData().observe(this) { data ->
            name = data.name
        }
    }

    private val storyAddLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            storyAdapter.refresh()
            // Optionally, scroll the recycler view to the top
            Handler(Looper.getMainLooper()).postDelayed({
                binding.rvStory.scrollToPosition(0)
            }, 200)
        }
    }

    private fun getStories() {
        binding.apply {
            rvStory.adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    storyAdapter.retry()
                }
            )
            viewModel.story.observe(this@StoryActivity) {
                storyAdapter.submitData(lifecycle, it)
            }

            storyAdapter.addLoadStateListener { loadState ->
                if (loadState.refresh is LoadState.NotLoading) {
                    progressBar.visibility = View.GONE

                    // Update your UI based on whether data exists
                    binding.tvNoData.visibility = if (storyAdapter.itemCount == 0) View.VISIBLE else View.GONE
                }
            }
        }
    }

    fun confirmLogout() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.confirm_logout))
            .setMessage(getString(R.string.confirm_logout_message))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModelMain.logout()
                val intent = Intent(this@StoryActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }
}