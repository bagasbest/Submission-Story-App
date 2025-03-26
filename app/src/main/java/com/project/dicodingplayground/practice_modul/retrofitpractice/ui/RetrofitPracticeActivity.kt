package com.project.dicodingplayground.practice_modul.retrofitpractice.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.project.dicodingplayground.databinding.ActivityRetrofitPracticeBinding
import com.project.dicodingplayground.practice_modul.retrofitpractice.data.response.CustomerReviewsItem
import com.project.dicodingplayground.practice_modul.retrofitpractice.data.response.Restaurant

class RetrofitPracticeActivity : AppCompatActivity() {

    private var _binding : ActivityRetrofitPracticeBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: RetrofitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRetrofitPracticeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

        viewmodel.restaurant.observe(this) {
            setDataRestaurant(it)
        }

        viewmodel.listReview.observe(this) {
            setListReview(it)
        }

        viewmodel.loading.observe(this) {
            showLoading(it)
        }

        viewmodel.showSnackBar.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(window.decorView.rootView, snackBarText, Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.btnSend.setOnClickListener { view ->
            val review = binding.edReview.text.toString().trim()

            if (review.isEmpty()) {
                Toast.makeText(this, "Review must be filled!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewmodel.postReview(review)
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setListReview(items: List<CustomerReviewsItem>) {
        val adapter = ReviewAdapter()
        adapter.submitList(items)
        binding.rvReview.adapter = adapter
        binding.edReview.setText("")
    }

    private fun setDataRestaurant(restaurant: Restaurant) {
        binding.tvTitle.text = restaurant.name
        binding.tvDescription.text = restaurant.description
        Glide.with(this)
            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
            .into(binding.ivPicture)
    }

    private fun initRecyclerView() {
        binding.apply {
            val layoutManager = LinearLayoutManager(this@RetrofitPracticeActivity)
            rvReview.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this@RetrofitPracticeActivity, layoutManager.orientation)
            rvReview.addItemDecoration(itemDecoration)
        }
    }

    companion object
}