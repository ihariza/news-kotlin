package com.nhariza.news.repository.database

import com.nhariza.news.repository.database.entity.ReportEntity


class NewsLocalDataSource constructor(private val reportDao: ReportDao) {

    suspend fun getNews(pageNumber: Int): List<ReportEntity>? = reportDao.getAll(pageNumber)

    suspend fun getReport(id: String?): ReportEntity? = reportDao.findBy(id)

    suspend fun save(report: ReportEntity) = reportDao.insert(report)

    suspend fun removeAll() = reportDao.deleteAll()
}