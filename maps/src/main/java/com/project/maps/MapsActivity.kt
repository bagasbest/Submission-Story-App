package com.project.maps

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.project.maps.databinding.ActivityMapsBinding
import com.project.tourismcore.data.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class MapsActivity : AppCompatActivity() {

    private var _binding: ActivityMapsBinding? = null
    private val binding get() = _binding!!
    private val mapsViewModel: MapsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(mapsModule)
        getTourismData()
    }

    private fun getTourismData() {
        mapsViewModel.tourism.observe(this) {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvMaps.text = "This is map of ${it.data?.get(0)?.name}"
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text = it.message
                    }
                }
            }
        }
    }
}