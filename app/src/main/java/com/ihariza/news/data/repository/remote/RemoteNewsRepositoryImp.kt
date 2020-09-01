package com.ihariza.news.data.repository.remote

import com.ihariza.news.BuildConfig
import com.ihariza.news.data.api.NewsApi
import com.ihariza.news.data.api.dto.toEntity
import com.ihariza.news.data.entity.ReportEntity
import com.ihariza.news.data.util.DateUtils

class RemoteNewsRepositoryImp (private val newsApi: NewsApi) : RemoteNewsRepository {

    override suspend fun getNews(pageNumber: Int): List<ReportEntity> =
            newsApi.getNews(
                    BuildConfig.NEWS_API,
                    "es",
                    "es",
                    DateUtils.getRFC3339Time(1),
                    pageNumber)
                    .news.toEntity()
}