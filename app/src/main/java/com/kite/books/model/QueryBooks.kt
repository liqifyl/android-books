package com.kite.books.model

import java.lang.StringBuilder

data class QueryBooks(var books: List<Book>? = null, var start: Int = 0, var count: Int = 0, var total: Int = 0) {
    override fun toString(): String {
        val stringBuilder = StringBuilder()
        books?.forEach {
            stringBuilder.append('[').append(it.toString()).append(']')
        }
        return "books:${stringBuilder.toString()}},start:$start,count:$count,total:$total"
    }
}