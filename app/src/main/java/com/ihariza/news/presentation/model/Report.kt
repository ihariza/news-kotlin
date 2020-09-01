package com.ihariza.news.presentation.model

import com.ihariza.news.domain.model.ReportBo
import com.ihariza.news.presentation.view.util.DateUtils

data class Report(
    val id: String,
    val title: String?,
    val description: String?,
    val url: String?,
    val author: String?,
    val image: String?,
    val published: String
)

fun ReportBo.toVm(): Report = Report(
        id,
        title,
        description,
        url,
        author,
        image,
        DateUtils.getRelativeTime(published)
)