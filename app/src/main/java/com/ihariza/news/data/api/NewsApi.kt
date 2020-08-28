package com.ihariza.news.data.api

import com.ihariza.news.data.api.dto.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("search")
    suspend fun getNews(
        @Query("apiKey") apiKey: String?,
        @Query("language") language: String?,
        @Query("country") country: String?,
        @Query("start_date") startDate: String?,
        @Query("page_number") pageNumber: Int
    ): NewsDto
}