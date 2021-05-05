package com.nhariza.news.repository

import com.nhariza.news.repository.model.Report

interface NewsRepository {

    suspend fun getNews(pageNumber: Int): List<Report>?

    suspend fun getReport(id: String): Report?
}