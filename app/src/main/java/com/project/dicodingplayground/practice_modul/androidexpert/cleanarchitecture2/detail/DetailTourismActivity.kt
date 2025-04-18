package com.project.dicodingplayground.practice_modul.androidexpert.cleanarchitecture2.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat.getParcelableExtra
import com.bumptech.glide.Glide
import com.project.dicodingplayground.R
import com.project.dicodingplayground.databinding.ActivityDetailTourismBinding
import com.project.tourismcore.domain.model.Tourism
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailTourismActivity : AppCompatActivity() {

    private var _binding: ActivityDetailTourismBinding? = null
    private val binding get() = _binding!!
    private val detailTourismViewModel: DetailTourismViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailTourismBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val detailTourism = getParcelableExtra(intent, EXTRA_DATA, Tourism::class.java)
        showDetailTourism(detailTourism)
    }

    private fun showDetailTourism(detailTourism: Tourism?) {
        detailTourism?.let {
            supportActionBar?.title = detailTourism.name
            binding.contentDetailTourism.tvDetailDescription.text = detailTourism.description
            Glide.with(this@DetailTourismActivity)
                .load(detailTourism.image)
                .into(binding.ivDetailImage)

            var statusFavorite = detailTourism.isFavorite
            setStatusFavorite(statusFavorite)
            binding.fab.setOnClickListener {
                statusFavorite = !statusFavorite
                detailTourismViewModel.setFavoriteTourism(detailTourism, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_note_favorite_white))
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}