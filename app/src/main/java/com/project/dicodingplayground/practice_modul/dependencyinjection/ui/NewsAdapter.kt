package com.project.dicodingplayground.practice_modul.dependencyinjection.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.dicodingplayground.R
import com.project.dicodingplayground.databinding.ItemNewsBinding
import com.project.dicodingplayground.practice_modul.dependencyinjection.data.local.entity.NewsEntity
import com.project.dicodingplayground.practice_modul.dependencyinjection.ui.NewsAdapter.NewsViewHolder
import com.project.dicodingplayground.practice_modul.dependencyinjection.utils.DateFormatter

class NewsAdapter(private val onClickBookmark: (NewsEntity) -> Unit) : ListAdapter<NewsEntity, NewsViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
       val view = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
       holder.bind(getItem(position))
    }

    inner class NewsViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsEntity) {
            binding.apply {
                binding.tvItemTitle.text = news.title
                binding.tvItemPublishedDate.text = DateFormatter.formatDate(news.publishedAt)
                Glide.with(itemView.context)
                    .load(news.urlToImage)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(binding.imgPoster)
                itemView.setOnClickListener {
//                    val intent = Intent(Intent.ACTION_VIEW)
//                    intent.data = Uri.parse(news.url)
//                    itemView.context.startActivity(intent)
                }
                val isBookmarked = news.isBookmarked
                if (isBookmarked) {
                    binding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.baseline_bookmark_24))
                } else {
                    binding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.outline_bookmark_border_24))
                }
                binding.ivBookmark.setOnClickListener {
                    onClickBookmark(news)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<NewsEntity> =
            object : DiffUtil.ItemCallback<NewsEntity>() {
                override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                    return oldItem.title == newItem.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }

}