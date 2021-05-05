package com.nhariza.news.repository.datasource

import com.nhariza.news.BuildConfig
import com.nhariza.news.common.DateFormatter
import com.nhariza.news.repository.datasource.model.NewsDto

class NewsRemoteDataSource(private val newsService: NewsService) {

    suspend fun getNews(pageNumber: Int): NewsDto =
        newsService.getNews(
            BuildConfig.NEWS_API,
            "es",
            "es",
            DateFormatter.getRFC3339Time(1),
            pageNumber
        )
}