package com.ihariza.news.data.api.dto

import com.ihariza.news.data.entity.ReportEntity
import com.ihariza.news.data.util.DateUtil

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

fun ReportDto.toEntity(): ReportEntity = ReportEntity(
        id,
        title,
        description,
        url,
        author,
        image,
        DateUtil.getLongTime(published),
        0
)

fun List<ReportDto>.toEntity(): List<ReportEntity> = map { it.toEntity() }