package com.nhariza.news.repository.model

data class Report(
    val id: String,
    val title: String?,
    val description: String?,
    val url: String?,
    val author: String?,
    val image: String?,
    val published: Long
)