package com.nhariza.news.builder

import com.nhariza.news.repository.datasource.model.NewsDto

object NewsDtoBuilder {

    fun build(): NewsDto = NewsDto("200", 2, ReportDtoBuilder.buildDtoList())
}