package com.project.dicodingplayground.submission_story_app.view.story

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.project.dicodingplayground.R
import com.project.dicodingplayground.databinding.ItemStoryBinding
import com.project.dicodingplayground.submission_story_app.data.api.response.ListStoryItem

class StoryAdapter() :
    PagingDataAdapter<ListStoryItem, StoryAdapter.ListViewHolder>(DIFF_CALLBACK) {

    class ListViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(storyItem: ListStoryItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(storyItem.photoUrl)
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
                    .transform(RoundedCorners(itemView.context.resources.getDimensionPixelSize(R.dimen.size_10dp)))
                    .into(ivStory)

                tvName.text = storyItem.name
                tvDescription.text = storyItem.description

                itemStory.setOnClickListener {
                    val intent = Intent(itemView.context, StoryDetailActivity::class.java)
                    intent.putExtra(StoryDetailActivity.EXTRA_DATA, storyItem)

                    val actionCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(ivStory, "profile"),
                        Pair(tvName, "name"),
                        Pair(tvDescription, "description")
                    )
                    itemView.context.startActivity(intent, actionCompat.toBundle())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}