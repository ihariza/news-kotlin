package com.ihariza.news.data.repository.local

import com.ihariza.news.data.entity.ReportEntity

interface LocalNewsRepository {
    suspend fun getNews(pageNumber: Int): List<ReportEntity>?
    suspend fun getReport(id: String?): ReportEntity?
    suspend fun save(report: ReportEntity)
    suspend fun removeAll()
}