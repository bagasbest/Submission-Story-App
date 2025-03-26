package com.project.dicodingplayground.submission_story_app.view.story

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.project.dicodingplayground.R
import com.project.dicodingplayground.databinding.ActivityMaps2Binding
import com.project.dicodingplayground.practice_modul.googlemaps.MapsActivity
import com.project.dicodingplayground.submission_story_app.data.Result
import com.project.dicodingplayground.submission_story_app.data.api.response.ListStoryItem
import com.project.dicodingplayground.submission_story_app.view.ViewModelFactory
import kotlin.getValue

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var _binding: ActivityMaps2Binding? = null
    private val binding get() = _binding!!
    private lateinit var mMap: GoogleMap
    private val boundsBuilder = LatLngBounds.Builder()
    private val storyList = ArrayList<ListStoryItem>()
    private val viewModel: StoryViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMaps2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.getAllStory().observe(this) { result ->
            if (result != null && result is Result.Success) {
                storyList.addAll(result.data)
                addManyMarker()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        getMyLocation()
        setMapStyle()
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.uiSettings.isMyLocationButtonEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun setMapStyle() {
        try {
            val success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(TAG, "style parsing failed")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }

    private fun addManyMarker() {
        try {
            val name = intent.getStringExtra(EXTRA_NAME)
            storyList.forEach { story ->
                val latLng = LatLng(story.lat!!, story.lon!!)
                val markerColor = if (name == story.name) {
                    BitmapDescriptorFactory.HUE_BLUE
                } else {
                    BitmapDescriptorFactory.HUE_RED
                }
                mMap.addMarker(MarkerOptions().position(latLng).title(story.name).snippet(story.description).icon(BitmapDescriptorFactory.defaultMarker(markerColor)))
                boundsBuilder.include(latLng)
            }

            val bounds: LatLngBounds = boundsBuilder.build()
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bounds,
                    resources.displayMetrics.widthPixels,
                    resources.displayMetrics.heightPixels,
                    300
                )
            )
        } catch (_ : Exception) {}
    }

    companion object {
        private val TAG = MapsActivity::class.java.simpleName
        const val EXTRA_NAME = "name"
    }
}