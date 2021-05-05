package com.nhariza.news.repository.datasource.model

data class ReportDto(
    val id: String,
    val title: String?,
    val description: String?,
    val url: String?,
    val author: String?,
    val image: String?,
    val language: String?,
    val category: List<String>?,
    val published: String?
)