package com.project.dicodingplayground.submission_fundamental_android.ui.events

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.project.dicodingplayground.R
import com.project.dicodingplayground.databinding.ActivityEventDetailNewBinding
import com.project.dicodingplayground.submission_fundamental_android.data.local.entity.EventEntity
import com.project.dicodingplayground.submission_fundamental_android.helper.DateFormatter

class EventDetailActivity : AppCompatActivity() {

    private var _binding : ActivityEventDetailNewBinding? = null
    private val binding get() = _binding!!
    private var event: EventEntity? = null
    private val viewModel: EventViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEventDetailNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            event = intent.getParcelableExtra(EXTRA_DATA)

            Glide.with(this@EventDetailActivity)
                .load(event?.mediaCover)
                .error(R.drawable.ic_error)
                .into(ivImage)

            tvName.text = event?.name
            tvCategory.text = event?.category
            tvCityName.text = event?.cityName
            tvBeginTime.text = "${getString(R.string.begin_time)} ${DateFormatter.formatDate(event?.beginTime.toString(), "yyyy-MM-dd HH:mm:ss", "EEEE, dd MMMM yyyy - HH:mm")}"
            tvEndTime.text = "${getString(R.string.end_time)} ${DateFormatter.formatDate(event?.endTime.toString(), "yyyy-MM-dd HH:mm:ss", "EEEE, dd MMMM yyyy - HH:mm")}"
            tvOwnerName.text = "${getString(R.string.owner_name)} ${event?.ownerName}"
            tvQuota.text = "${getString(R.string.quota)} ${event?.quota}"
            tvRegistrant.text = "${getString(R.string.registrant)} ${event?.registrants}"
            tvLink.text = event?.link
            tvLink.paintFlags = tvLink.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            tvLink.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, event?.link?.toUri())
                startActivity(intent)
            }

            try {
                val pre = """
                        <html>
                        <head>
                          <style type="text/css">
                            @font-face {
                              font-family: MyFont;
                              src: url("file:///android_asset/fonts/Varela-Round-Regular.ttf");
                            }
                             body {
                              font-family: MyFont;
                              margin: 0;
                              padding: 0 1px;
                              line-height: 1.6;
                              color: #000000; /* Default for light mode */
                              background-color: #FFFFFF;
                            }
                            @media (prefers-color-scheme: dark) {
                              body {
                                color: #FFFFFF; /* Text color for dark mode */
                                background-color: #121212; /* Background color for dark mode */
                              }
                            }
                            img {
                              width: 100%;
                              height: auto;
                              object-fit: cover;
                              object-position: center;
                              display: block;
                              margin-left: auto;
                              margin-right: auto;
                            }
                          </style>
                        </head>
                        <body>
                    """.trimIndent()

                val post = "</body></html>"
                val htmlData = event?.description?.replace("<br>", "", ignoreCase = true)
                val myHtmlString = pre + htmlData + post

                binding.tvDescription.loadDataWithBaseURL(null, myHtmlString, "text/html", "utf-8", null)
                binding.tvDescription.setBackgroundColor(ContextCompat.getColor(this@EventDetailActivity, R.color.transparent))
                binding.tvDescription.visibility = View.VISIBLE
            } catch (_: Exception){
                binding.tvDescription.visibility = View.GONE
            }

            if (event?.isFavorites == true) {
                setFavorite()
            }

            btnBack.setOnClickListener {
                finish()
            }

            btnFavorite.setOnClickListener {
                if (event?.isFavorites == true) {
                    setUnFavorite()
                    viewModel.deleteEvent(event!!)
                } else {
                    setFavorite()
                    viewModel.saveEvent(event!!)
                }
            }
        }
    }

    private fun setUnFavorite() {
        binding.apply {
            btnFavorite.setImageResource(R.drawable.ic_unfavorite)
            btnFavorite.imageTintList = ContextCompat.getColorStateList(this@EventDetailActivity, android.R.color.white)
        }
    }

    private fun setFavorite() {
        binding.apply {
            btnFavorite.setImageResource(R.drawable.baseline_favorite_24)
            btnFavorite.imageTintList = ContextCompat.getColorStateList(this@EventDetailActivity, android.R.color.holo_red_dark)
        }
    }

    companion object {
        const val EXTRA_DATA = "data"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}