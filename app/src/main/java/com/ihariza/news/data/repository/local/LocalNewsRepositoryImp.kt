package com.ihariza.news.data.repository.local

import com.ihariza.news.data.database.ReportDao
import com.ihariza.news.data.entity.ReportEntity


class LocalNewsRepositoryImp constructor(private val reportDao: ReportDao) : LocalNewsRepository {

    override suspend fun getNews(pageNumber: Int): List<ReportEntity>? {
        return reportDao.getAll(pageNumber)
    }

    override suspend fun getReport(id: String?): ReportEntity? {
        return reportDao.findBy(id)
    }

    override suspend fun save(report: ReportEntity?) {
        return reportDao.insert(report)
    }

    override suspend fun removeAll() {
        return reportDao.deleteAll()
    }
}