package com.nhariza.news.repository

import com.nhariza.news.repository.database.NewsLocalDataSource
import com.nhariza.news.repository.datasource.NewsRemoteDataSource
import com.nhariza.news.repository.model.Report


class NewsRepositoryImp(
    private val newsLocalDataSource: NewsLocalDataSource,
    private val newsRemoteDataSource: NewsRemoteDataSource
) : NewsRepository {

    override suspend fun getNews(pageNumber: Int): List<Report>? {
        if (pageNumber == 1) {
            newsLocalDataSource.removeAll()
        }
        newsRemoteDataSource.getNews(pageNumber).news
            .map { newsLocalDataSource.save(it.toEntity().copy(page = pageNumber)) }
        return newsLocalDataSource.getNews(pageNumber)?.toModel()
    }

    override suspend fun getReport(id: String): Report? {
        return newsLocalDataSource.getReport(id)?.toModel()
    }
}