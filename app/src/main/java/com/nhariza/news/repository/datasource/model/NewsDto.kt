package com.nhariza.news.repository.datasource.model

data class NewsDto(
    val status: String?,
    val totalResults: Int,
    val news: List<ReportDto>
)