package com.project.dicodingplayground.submission_story_app.utils

import com.project.dicodingplayground.submission_story_app.data.api.response.ListStoryItem

object DataDummy {
    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                "photo $i",
                "created at $i",
                "name $i",
                "description $i",
                0.0,
                i.toString(),
                0.0
            )
            items.add(story)
        }
        return items
    }
}