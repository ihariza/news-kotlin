package com.ihariza.news.domain.model

data class ReportBo(
    val id: String,
    val title: String?,
    val description: String?,
    val url: String?,
    val author: String?,
    val image: String?,
    val published: Long
)