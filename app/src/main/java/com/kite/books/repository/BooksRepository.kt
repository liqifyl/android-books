package com.kite.books.repository

import android.util.Log
import com.kite.books.constant.Server
import com.kite.books.model.QueryBooks
import com.kite.books.restful.BookQueryService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class BooksRepository {
    companion object {
        private const val TAG = "BooksRepository"
        /**
         * 查询书籍
         * @param query 关键字
         * @param returnFields 返回字段的集合
         */
        fun getBooksByQuery(query: String, returnFields: List<String>?): QueryBooks? {
            if (returnFields == null || returnFields.isEmpty()) return null
            val retrofit = Retrofit.Builder()
                    .baseUrl(Server.RELEASE_SERVER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            val bookSearchService = retrofit.create(BookQueryService::class.java)
            try {
                val stringBuilder = StringBuilder()
                returnFields.forEachIndexed { index, s ->
                    if (index != returnFields.size - 1) {
                        stringBuilder.append(s)
                        stringBuilder.append(',')
                    } else {
                        stringBuilder.append(s)
                    }
                }
                Log.d(TAG, "fields:$returnFields")
                return bookSearchService.searchBook(query, stringBuilder.toString(), Server.APP_KEY).execute().body()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

//        fun getBooksByQueryWithRxJava(query: String, returnFields: List<String>?): QueryBooks? {
//            if (returnFields == null || returnFields.isEmpty()) return null
//            val retrofit = Retrofit.Builder()
//                    .baseUrl(Server.RELEASE_SERVER)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .build()
//            val bookSearchService = retrofit.create(BookQueryService::class.java)
//            try {
//                val stringBuilder = StringBuilder()
//                returnFields.forEachIndexed { index, s ->
//                    if (index != returnFields.size - 1) {
//                        stringBuilder.append(s)
//                        stringBuilder.append(',')
//                    } else {
//                        stringBuilder.append(s)
//                    }
//                }
//                //We need use async when we use RxJava
//                bookSearchService.searchBook(query, stringBuilder.toString(), Server.APP_KEY).enqueue(object : Callback<QueryBooks?> {
//                    override fun onFailure(call: Call<QueryBooks?>, t: Throwable) {
//
//                    }
//
//                    override fun onResponse(call: Call<QueryBooks?>, response: Response<QueryBooks?>) {
//                    }
//                })
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//            return null
//        }
    }
}