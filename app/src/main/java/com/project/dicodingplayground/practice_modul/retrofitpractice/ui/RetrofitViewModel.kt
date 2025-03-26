package com.project.dicodingplayground.practice_modul.retrofitpractice.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.dicodingplayground.practice_modul.retrofitpractice.data.response.CustomerReviewsItem
import com.project.dicodingplayground.practice_modul.retrofitpractice.data.response.PostReviewResponse
import com.project.dicodingplayground.practice_modul.retrofitpractice.data.response.Restaurant
import com.project.dicodingplayground.practice_modul.retrofitpractice.data.response.RestaurantResponse
import com.project.dicodingplayground.practice_modul.retrofitpractice.data.retrofit.ApiConfig
import com.project.dicodingplayground.practice_modul.retrofitpractice.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitViewModel : ViewModel() {

    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = _restaurant

    private val _listReview = MutableLiveData<List<CustomerReviewsItem>>()
    val listReview: LiveData<List<CustomerReviewsItem>> = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _isLoading

    private val _showSnackBar = MutableLiveData<Event<String>>()
    val showSnackBar: LiveData<Event<String>> = _showSnackBar

    init {
        findRestaurant()
    }

    private fun findRestaurant() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)
        client.enqueue(object : Callback<RestaurantResponse> {
            override fun onResponse(
                call: Call<RestaurantResponse?>,
                response: Response<RestaurantResponse?>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _restaurant.value = responseBody.restaurant
                        _listReview.value = responseBody.restaurant.customerReviews
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(
                call: Call<RestaurantResponse?>,
                t: Throwable
            ) {
                _isLoading.value = false
                Log.e(TAG, "onFailure ${t.message}")
            }
        })
    }

    fun postReview(review: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postReview(RESTAURANT_ID, "John Doe", review)
        client.enqueue(object : Callback<PostReviewResponse> {
            override fun onResponse(
                call: Call<PostReviewResponse?>,
                response: Response<PostReviewResponse?>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _listReview.value = responseBody.customerReviews
                    _showSnackBar.value = Event(responseBody.message)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PostReviewResponse?>, t: Throwable) {
               _isLoading.value = false
                Log.e(TAG, "onFailure ${t.message}")
            }
        })
    }


    companion object {
        private const val TAG = "RetrofitViewModel"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }
}