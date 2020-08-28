package com.ihariza.news.domain.usecase

import com.ihariza.news.domain.model.ReportBo
import com.ihariza.news.domain.repository.NewsRepository


class GetReportUseCase (private val newsRepository: NewsRepository) {
    suspend fun getReport(id: String): ReportBo? = newsRepository.getReport(id)
}