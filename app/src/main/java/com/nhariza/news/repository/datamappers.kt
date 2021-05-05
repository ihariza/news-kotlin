package com.nhariza.news.repository


import com.nhariza.news.common.DateFormatter
import com.nhariza.news.repository.database.entity.ReportEntity
import com.nhariza.news.repository.datasource.model.ReportDto
import com.nhariza.news.repository.model.Report

fun ReportDto.toEntity(): ReportEntity = ReportEntity(
    id,
    title,
    description,
    url,
    author,
    image,
    DateFormatter.getLongTime(published),
    0
)

fun List<ReportDto>.toEntity(): List<ReportEntity> = map { it.toEntity() }

fun ReportEntity.toModel(): Report = Report(
    id,
    title,
    description,
    url,
    author,
    image,
    published
)

fun List<ReportEntity>.toModel(): List<Report> = map { it.toModel() }