package com.ihariza.news.data.repository.remote

import com.ihariza.news.data.entity.ReportEntity

interface RemoteNewsRepository {
    suspend fun getNews(pageNumber: Int): List<ReportEntity>
}