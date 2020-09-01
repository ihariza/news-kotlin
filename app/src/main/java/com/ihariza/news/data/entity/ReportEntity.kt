package com.ihariza.news.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ihariza.news.domain.model.ReportBo

@Entity(tableName = "report")
data class ReportEntity(
        @PrimaryKey
        val id: String,
        val title: String?,
        val description: String?,
        val url: String?,
        val author: String?,
        val image: String?,
        val published: Long,
        val page: Int
)

fun ReportEntity.toBo(): ReportBo = ReportBo(
        id,
        title,
        description,
        url,
        author,
        image,
        published
)

fun List<ReportEntity>.toBo(): List<ReportBo> = map { it.toBo() }