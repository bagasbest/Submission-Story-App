package com.project.dicodingplayground.submission_story_app.view.story

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.project.dicodingplayground.R
import com.project.dicodingplayground.databinding.ActivityStoryAddBinding
import com.project.dicodingplayground.submission_story_app.data.Result
import com.project.dicodingplayground.submission_story_app.utils.ImageProcessing
import com.project.dicodingplayground.submission_story_app.utils.ImageProcessing.getImageUri
import com.project.dicodingplayground.submission_story_app.utils.ImageProcessing.reduceFileImage
import com.project.dicodingplayground.submission_story_app.view.ViewModelFactory
import kotlin.getValue
import androidx.core.net.toUri
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import java.util.concurrent.TimeUnit

class StoryAddActivity : AppCompatActivity() {

    private var _binding: ActivityStoryAddBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
    private var currentImageUriTemp: Uri? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private var currentLatLng: LatLng? = null
    private val viewModel by viewModels<StoryViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStoryAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createLocationRequest()

        savedInstanceState?.getString(IMAGE_URI_KEY)?.let {
            currentImageUri = it.toUri()
            showImage()
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.uploadButton.setOnClickListener { uploadImage() }
    }

    private val requestPermissionLocationLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permission ->
            when {
                permission[Manifest.permission.ACCESS_FINE_LOCATION] == true -> {
                    // Precise location access granted.
                    getMyLastLocation()
                }

                permission[Manifest.permission.ACCESS_COARSE_LOCATION] == true -> {
                    // Only approximate location access granted.
                    getMyLastLocation()
                }

                permission[Manifest.permission.CAMERA] == true -> {
                    // Camera permission granted.
                }

                else -> {
                    // No location access granted.
                }
            }
        }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyLastLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION) &&
            checkPermission(Manifest.permission.CAMERA)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    currentLatLng = LatLng(location.latitude, location.longitude)
                } else {
                    Toast.makeText(
                        this@StoryAddActivity,
                        "Location is not found. Try again!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            requestPermissionLocationLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CAMERA
                )
            )
        }
    }

    private val resolutionLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            when (result.resultCode) {
                RESULT_OK ->
                    Log.i(TAG, "onActivityResult: All location settings are satisfied.")

                RESULT_CANCELED ->
                    Toast.makeText(
                        this@StoryAddActivity,
                        getString(R.string.you_must_active_gps),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }


    private fun createLocationRequest() {
        val priority = Priority.PRIORITY_HIGH_ACCURACY
        val interval = TimeUnit.SECONDS.toMillis(1)
        val maxWaitTime = TimeUnit.SECONDS.toMillis(1)
        locationRequest = LocationRequest.Builder(
            priority,
            interval
        ).apply {
            setMaxUpdateDelayMillis(maxWaitTime)
        }.build()
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this)
        client.checkLocationSettings(builder.build())
            .addOnSuccessListener {
                getMyLastLocation()
            }
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        resolutionLauncher.launch(
                            IntentSenderRequest.Builder(exception.resolution).build()
                        )
                    } catch (sendEx: IntentSender.SendIntentException) {
                        Toast.makeText(this@StoryAddActivity, sendEx.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
    }

    private fun uploadImage() {
        val description = binding.etDescription.text.toString()
        val confirmAddLocation = binding.cbConfirmLocationAdded.isChecked
        if (confirmAddLocation && currentLatLng == null) {
            showToast(getString(R.string.please_allow_all_permission_to_post_story))
        } else if (currentImageUri == null) {
            showToast(getString(R.string.empty_image))
        } else if (description.isEmpty()) {
            showToast(getString(R.string.empty_description))
        } else {
            val imageFile = ImageProcessing.uriToFile(currentImageUri!!, this).reduceFileImage()

            val payload = StoryAddPayload(
                imageFile,
                description,
                currentLatLng?.latitude,
                currentLatLng?.longitude
            )
            viewModel.uploadImage(payload).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            showToast(result.data.message.toString())
                            showLoading(false)
                            binding.etDescription.setText("")
                            currentImageUri = null
                            setResult(RESULT_OK)
                            finish()
                        }

                        is Result.Error -> {
                            showToast(result.error)
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
            currentImageUri = currentImageUriTemp
        }
    }

    private fun startCamera() {
        currentImageUriTemp = currentImageUri
        currentImageUri = getImageUri(this)
        launcherCamera.launch(currentImageUri!!)
    }


    private fun showImage() {
        currentImageUri.let {
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        currentImageUri?.let {
            outState.putString(IMAGE_URI_KEY, it.toString())
        }
    }

    companion object {
        private const val IMAGE_URI_KEY = "IMAGE_URI_KEY"
        private val TAG = StoryAddActivity::class.java.simpleName
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}