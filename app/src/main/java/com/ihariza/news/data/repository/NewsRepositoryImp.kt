package com.ihariza.news.data.repository

import com.ihariza.news.data.entity.toBo
import com.ihariza.news.data.repository.local.LocalNewsRepository
import com.ihariza.news.data.repository.remote.RemoteNewsRepository
import com.ihariza.news.domain.model.ReportBo
import com.ihariza.news.domain.repository.NewsRepository

class NewsRepositoryImp(private val localNewsRepository: LocalNewsRepository,
                         private val remoteNewsRepository: RemoteNewsRepository) : NewsRepository {

    override suspend fun getNews(pageNumber: Int): List<ReportBo> {
        if (pageNumber == 1) {
            localNewsRepository.removeAll()
        }
        remoteNewsRepository.getNews(pageNumber)
                .map { localNewsRepository.save(it.copy(page = pageNumber)) }
        return localNewsRepository.getNews(pageNumber)?.toBo() ?: listOf()
    }

    override suspend fun getReport(id: String): ReportBo? {
        return localNewsRepository.getReport(id)?.toBo()
    }
}