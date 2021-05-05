package com.nhariza.news.repository.datasource

import com.nhariza.news.repository.datasource.model.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("search")
    suspend fun getNews(
        @Query("apiKey") apiKey: String?,
        @Query("language") language: String?,
        @Query("country") country: String?,
        @Query("start_date") startDate: String?,
        @Query("page_number") pageNumber: Int
    ): NewsDto
}