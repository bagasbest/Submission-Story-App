package com.project.dicodingplayground.practice_modul.paging3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.dicodingplayground.databinding.ItemQuoteBinding
import com.project.dicodingplayground.practice_modul.paging3.network.QuoteResponseItem

class QuoteListAdapter :
    PagingDataAdapter<QuoteResponseItem, QuoteListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ItemQuoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: QuoteResponseItem) {
            binding.tvItemQuote.text = data.en
            binding.tvItemAuthor.text = data.author
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<QuoteResponseItem>() {
            override fun areItemsTheSame(
                oldItem: QuoteResponseItem,
                newItem: QuoteResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: QuoteResponseItem,
                newItem: QuoteResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}