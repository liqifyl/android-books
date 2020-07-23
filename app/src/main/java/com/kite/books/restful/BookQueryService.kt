package com.kite.books.restful

import retrofit2.http.GET
import retrofit2.http.Query
import com.kite.books.model.QueryBooks
import retrofit2.Call

interface BookQueryService {
    @GET("v2/book/search")
    fun searchBook(@Query("q") queryKey: String, @Query("fields") fields: String, @Query("apikey") apiKey: String): Call<QueryBooks?>
}