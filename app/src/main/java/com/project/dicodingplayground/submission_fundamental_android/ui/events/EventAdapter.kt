package com.project.dicodingplayground.submission_fundamental_android.ui.events

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.project.dicodingplayground.R
import com.project.dicodingplayground.databinding.ItemEventBinding
import com.project.dicodingplayground.submission_fundamental_android.data.local.entity.EventEntity

class EventAdapter : ListAdapter<EventEntity, EventAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var mClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClickEvent(event: EventEntity, adapterPosition: Int)
    }

    fun setOnItemClickListener(aClickListener: OnItemClickListener) {
        mClickListener = aClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    inner class MyViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventEntity) {
            binding.tvName.text = event.name
            binding.tvSummary.text = event.summary
            binding.tvCategory.text = event.category

            Glide.with(itemView.context)
                .load(event.imageLogo)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                        .transform(RoundedCorners(24))
                )
                .into(binding.ivImage)

            itemView.setOnClickListener {
                mClickListener?.onClickEvent(event, adapterPosition)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>() {
            override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                // Customize this check according to your unique identifier
                return oldItem.name == newItem.name
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}