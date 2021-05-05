package com.nhariza.news.repository

import com.nhariza.news.builder.NewsDtoBuilder
import com.nhariza.news.builder.ReportDtoBuilder
import com.nhariza.news.repository.database.NewsLocalDataSource
import com.nhariza.news.repository.datasource.NewsRemoteDataSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyAll
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NewsRepositoryTest {

    companion object {
        private const val PAGE_ONE = 1
        private const val PAGE_TWO = 2
        private const val REPORT_ID = "1"
    }

    @RelaxedMockK
    lateinit var newsLocalDataSource: NewsLocalDataSource

    @RelaxedMockK
    lateinit var newsRemoteDataSource: NewsRemoteDataSource

    private lateinit var newsRepository: NewsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        newsRepository = NewsRepositoryImp(newsLocalDataSource, newsRemoteDataSource)
    }

    @Test
    fun `get page one news should remove news from local and returns a list of news`() {
        coEvery {
            newsRemoteDataSource.getNews(PAGE_ONE)
        } returns NewsDtoBuilder.build()

        coEvery {
            newsLocalDataSource.getNews(PAGE_ONE)
        } returns ReportDtoBuilder.buildDtoList().toEntity()

        val news = runBlocking { newsRepository.getNews(PAGE_ONE) }

        coVerifyAll {
            newsLocalDataSource.removeAll()
            newsLocalDataSource.save(any())
            newsLocalDataSource.getNews(PAGE_ONE)
            assertEquals(news, ReportDtoBuilder.buildDtoList().toEntity().toModel())
        }
    }

    @Test
    fun `get page two news should returns a list of news`() {
        coEvery {
            newsRemoteDataSource.getNews(PAGE_TWO)
        } returns NewsDtoBuilder.build()

        coEvery {
            newsLocalDataSource.getNews(PAGE_TWO)
        } returns ReportDtoBuilder.buildDtoList().toEntity()

        val news = runBlocking { newsRepository.getNews(PAGE_TWO) }

        coVerify(exactly = 0) { newsLocalDataSource.removeAll() }
        assertEquals(news, ReportDtoBuilder.buildDtoList().toEntity().toModel())
    }

    @Test
    fun `given a report id, should returns the report`() {
        coEvery {
            newsLocalDataSource.getReport(REPORT_ID)
        } returns ReportDtoBuilder.build().toEntity()

        val report = runBlocking { newsRepository.getReport(REPORT_ID) }

        assertEquals(report, ReportDtoBuilder.build().toEntity().toModel())
    }

    @Test
    fun `given an invalid report id, should returns null`() {
        coEvery {
            newsLocalDataSource.getReport(any())
        } returns null

        val report = runBlocking { newsRepository.getReport("invalid") }

        assert(report == null)
    }
}