package com.project.dicodingplayground.practice_modul.paging3.utils

import com.project.dicodingplayground.practice_modul.paging3.network.QuoteResponseItem

object DataDummy {
    fun generateDummyQuoteResponse(): List<QuoteResponseItem> {
        val items: MutableList<QuoteResponseItem> = arrayListOf()
        for (i in 0..100) {
            val quote = QuoteResponseItem(
                i.toString(),
                "author + $i",
                "quote $i",
            )
            items.add(quote)
        }
        return items
    }
}