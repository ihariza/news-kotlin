package com.ihariza.news.domain.repository

import com.ihariza.news.domain.model.ReportBo

interface NewsRepository {

    suspend fun getNews(pageNumber: Int): List<ReportBo>

    suspend fun getReport(id: String): ReportBo?
}