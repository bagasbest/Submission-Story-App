package com.project.dicodingplayground.practice_modul.transition.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.dicodingplayground.databinding.ItemRowHeroBinding
import com.project.dicodingplayground.practice_modul.transition.HeroActivity
import com.project.dicodingplayground.practice_modul.transition.model.Hero

class ListHeroAdapter(private val listHero: ArrayList<Hero>) : RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view = ItemRowHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listHero[position])
    }

    override fun getItemCount(): Int = listHero.size

    class ListViewHolder(private val binding: ItemRowHeroBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hero: Hero) {
            Glide.with(itemView.context)
                .load(hero.photo)
                .circleCrop()
                .into(binding.profileImageView)

            binding.nameTextView.text = hero.name
            binding.descTextView.text = hero.description

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, HeroActivity::class.java)
                intent.putExtra("Hero", hero)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.profileImageView, "profile"),
                        Pair(binding.nameTextView, "name"),
                        Pair(binding.descTextView, "description"),
                    )

                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }
}