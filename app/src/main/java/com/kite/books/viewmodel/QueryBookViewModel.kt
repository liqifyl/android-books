package com.kite.books.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kite.books.model.QueryBooks
import com.kite.books.repository.BooksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class QueryBookViewModel : ViewModel() {
    private val queryBooks = MutableLiveData<QueryBooks>()

    fun queryBooks(query: String, returnFields: List<String>?) {
        GlobalScope.launch(Dispatchers.Default) {
            var response: QueryBooks? = null
            val task = async {
                Log.d(TAG, "getBooksByQuery run on ${Thread.currentThread().name}")
                response = BooksRepository.getBooksByQuery(query, returnFields)
            }
            task.await()
            response?.let {
                it.books?.forEach { ele ->
                    var price = ele.price
                    price?.let {
                        var index = price.indexOf("元")
                        if (index != -1) {
                            ele.price = "¥${price.removeRange(index, index + 1)}"
                            return@forEach
                        }
                        index = price.indexOf("CNY")
                        if (index == -1) {
                            ele.price = "¥$price"
                            return@forEach
                        }
                        ele.price = "¥${price.removeRange(index, index + 4)}"
                    }
                }
                Log.d(TAG, "$it")
            }

            response?.let {
                queryBooks.postValue(it)
            }
        }
    }

    fun getQueryBooks(): LiveData<QueryBooks> {
        return queryBooks
    }

    companion object {
        const val TAG = "QueryBookViewModel"
    }
}