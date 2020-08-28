package com.ihariza.news.data.api.dto

data class NewsDto (
    val status: String?,
    val totalResults: Int,
    val news: List<ReportDto>
)