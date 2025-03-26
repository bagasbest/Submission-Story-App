package com.project.dicodingplayground.practice_modul.transition

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.dicodingplayground.R
import com.project.dicodingplayground.databinding.ActivityMain3Binding
import com.project.dicodingplayground.practice_modul.transition.adapter.ListHeroAdapter
import com.project.dicodingplayground.practice_modul.transition.model.Hero

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMain3Binding? = null
    private val binding get() = _binding!!
    private lateinit var rvHeroes: RecyclerView
    private val list = ArrayList<Hero>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       _binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        rvHeroes = findViewById(R.id.rv_heroes)
        rvHeroes.setHasFixedSize(true)

        list.addAll(listHeroes)
        showRecyclerList()
    }

    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvHeroes.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvHeroes.layoutManager = LinearLayoutManager(this)
        }
        val listHeroAdapter = ListHeroAdapter(list)
        rvHeroes.adapter = listHeroAdapter
    }

    private val listHeroes: ArrayList<Hero>
        get() {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataDescription = resources.getStringArray(R.array.data_description)
            val dataPhoto = resources.getStringArray(R.array.data_photo)
            val listHero = ArrayList<Hero>()
            for (i in dataName.indices) {
                val hero = Hero(dataName[i], dataDescription[i], dataPhoto[i])
                listHero.add(hero)
            }
            return listHero
        }
}