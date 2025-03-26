package com.project.dicodingplayground.practice_modul.tdd.utils

import com.project.dicodingplayground.practice_modul.tdd.data.local.entity.NewsEntity

object DataDummy {
    fun generateDummyNewsEntity(): List<NewsEntity> {
        val newsList = ArrayList<NewsEntity>()
        for (i in 0..10) {
            val news = NewsEntity(
                "Title $i",
                "2022-02-22T22:22:22Z",
                "https://assets.cdn.dicoding.com/original/commons/feature-1-kurikulum-global-3.png",
                "https://www.dicoding.com/",
            )
            newsList.add(news)
        }
        return newsList
    }

}