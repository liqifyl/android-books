package com.kite.books.viewmodel

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kite.books.R
import com.kite.books.annotation.LayoutId
import com.kite.books.model.Book

@LayoutId(id = R.layout.item_book)
class ItemBookViewModel {
    private val TAG = " ItemBookViewModel"

    var title: String? = null

    var imageUrl: String? = null

    var price: String? = null

    var publisher: String? = null

    private var id: Long? = null

    fun updateBook(book: Book?, imageView: ImageView, context: Context) {
        title = book?.title
        imageUrl = book?.image
        id = book?.id ?: -1
        price = book?.price
        publisher = book?.publisher
        Glide.with(context).load(imageUrl).into(imageView)
    }

    fun bookDetail() {
        //jump to book detail activity
    }
}