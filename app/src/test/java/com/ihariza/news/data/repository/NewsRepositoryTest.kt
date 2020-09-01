package com.ihariza.news.data.repository

import com.ihariza.news.data.api.dto.toEntity
import com.ihariza.news.data.entity.toBo
import com.ihariza.news.data.repository.local.LocalNewsRepository
import com.ihariza.news.data.repository.remote.RemoteNewsRepository
import com.ihariza.news.domain.repository.NewsRepository
import com.ihariza.news.fake.FakeNewsTest
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class NewsRepositoryTest {

    @RelaxedMockK
    lateinit var localNewsRepository: LocalNewsRepository

    @RelaxedMockK
    lateinit var remoteNewsRepository: RemoteNewsRepository

    private lateinit var newsRepository: NewsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        newsRepository = NewsRepositoryImp(localNewsRepository, remoteNewsRepository)
    }

    @Test
    fun `get page one news should remove news from local and returns a list of news`() {
        coEvery {
            remoteNewsRepository.getNews(FakeNewsTest.PAGE_ONE)
        } returns FakeNewsTest.getReportDtoList().toEntity()

        coEvery {
            localNewsRepository.getNews(FakeNewsTest.PAGE_ONE)
        } returns FakeNewsTest.getReportDtoList().toEntity()

        val news = runBlocking { newsRepository.getNews(FakeNewsTest.PAGE_ONE) }

        coVerifyAll {
            localNewsRepository.removeAll()
            localNewsRepository.save(any())
            localNewsRepository.getNews(FakeNewsTest.PAGE_ONE)
            assert(news == FakeNewsTest.getReportDtoList().toEntity().toBo())
        }
    }

    @Test
    fun `get page two news should returns a list of news`() {
        coEvery {
            remoteNewsRepository.getNews(FakeNewsTest.PAGE_TWO)
        } returns FakeNewsTest.getReportDtoList().toEntity()

        coEvery {
            localNewsRepository.getNews(FakeNewsTest.PAGE_TWO)
        } returns FakeNewsTest.getReportDtoList().toEntity()

        val news = runBlocking { newsRepository.getNews(FakeNewsTest.PAGE_TWO) }

        coVerify(exactly = 0) { localNewsRepository.removeAll() }
        assert(news == FakeNewsTest.getReportDtoList().toEntity().toBo())
    }

    @Test
    fun  `given a report id, should returns the reportBo`() {
        coEvery {
            localNewsRepository.getReport(FakeNewsTest.REPORT_ID)
        } returns FakeNewsTest.getReportDto().toEntity()

        val reportBo = runBlocking { newsRepository.getReport(FakeNewsTest.REPORT_ID) }

        assert(reportBo == FakeNewsTest.getReportDto().toEntity().toBo())
    }

    @Test
    fun  `given an invalid report id, should returns null`() {
        coEvery {
            localNewsRepository.getReport(any())
        } returns null

        val reportBo = runBlocking { newsRepository.getReport("invalid") }

        assert(reportBo == null)
    }
}