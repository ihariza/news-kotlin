package com.ihariza.news.domain.usecase

import com.ihariza.news.domain.model.ReportBo
import com.ihariza.news.domain.repository.NewsRepository


class GetNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun getNews(pageNumber: Int): List<ReportBo> =
            newsRepository.getNews(pageNumber)
                    .distinctBy { it.id }
                    .filterNot { it.published == 0L }
                    .sortedByDescending { it.published }
}