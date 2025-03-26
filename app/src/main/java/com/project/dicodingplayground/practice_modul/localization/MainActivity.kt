package com.project.dicodingplayground.practice_modul.localization

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.project.dicodingplayground.R
import com.project.dicodingplayground.databinding.ActivityMain5Binding
import com.project.dicodingplayground.practice_modul.localization.Helper.withCurrencyFormat
import com.project.dicodingplayground.practice_modul.localization.Helper.withDateFormat
import com.project.dicodingplayground.practice_modul.localization.Helper.withNumberingFormat

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMain5Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMain5Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
        setupData()
    }

    private fun setupAction() {
        binding.settingImageView.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
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

    private fun setupData() {
        val repository = RemoteDataSource(this)
        val product = repository.getDetailProduct().apply {
            binding.apply {
                previewImageView.setImageResource(image)
                nameTextView.text = name
                storeTextView.text = store
                colorTextView.text = color
                sizeTextView.text = size
                descTextView.text = desc
                priceTextView.text = price.withCurrencyFormat()
                dateTextView.text = getString(R.string.dateFormat, date.withDateFormat())
                ratingTextView.text = getString(R.string.ratingFormat, rating.withNumberingFormat(), countRating.withNumberingFormat())
            }
        }
        setupAccessibility(product)
    }

    private fun setupAccessibility(model: ProductModel) {
        model.apply {
            binding.apply {
                settingImageView.contentDescription = getString(R.string.settingDescription)
                previewImageView.contentDescription = getString(R.string.previewDescription)
                colorTextView.contentDescription = getString(R.string.colorDescription, color)
                sizeTextView.contentDescription = getString(R.string.sizeDescription, size)
                ratingTextView.contentDescription = getString(
                    R.string.ratingDescription,
                    rating.withNumberingFormat(),
                    countRating.withNumberingFormat()
                )
                storeTextView.contentDescription = getString(R.string.storeDescription, store)
            }
        }
    }
}