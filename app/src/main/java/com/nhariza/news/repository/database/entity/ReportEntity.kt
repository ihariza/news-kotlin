package com.nhariza.news.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

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