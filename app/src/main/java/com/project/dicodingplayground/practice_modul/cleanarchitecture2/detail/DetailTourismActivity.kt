package com.project.dicodingplayground.practice_modul.cleanarchitecture2.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat.getParcelableExtra
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.project.dicodingplayground.R
import com.project.dicodingplayground.databinding.ActivityDetailTourismBinding
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.data.source.local.entity.TourismEntity
import com.project.dicodingplayground.practice_modul.cleanarchitecture2.core.ui.ViewModelFactory

class DetailTourismActivity : AppCompatActivity() {

    private var _binding: ActivityDetailTourismBinding? = null
    private val binding get() = _binding!!
    private val detailTourismViewModel: DetailTourismViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailTourismBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val detailTourism = getParcelableExtra(intent, EXTRA_DATA, TourismEntity::class.java)
        showDetailTourism(detailTourism)
    }

    private fun showDetailTourism(detailTourism: TourismEntity?) {
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