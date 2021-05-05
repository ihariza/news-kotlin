package com.nhariza.news.repository.database


import com.nhariza.news.builder.ReportDtoBuilder
import com.nhariza.news.repository.database.entity.ReportEntity
import com.nhariza.news.repository.toEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NewsLocalDataSourceTest {

    companion object {
        private const val PAGE_ONE = 1
        private const val REPORT_ID = "1"
    }

    @RelaxedMockK
    lateinit var reportDao: ReportDao

    private lateinit var newsLocalDataSource: NewsLocalDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        newsLocalDataSource = NewsLocalDataSource(reportDao)
    }

    @Test
    fun `get news should returns a list of news`() {
        val reportList = ReportDtoBuilder.buildDtoList().toEntity()
        coEvery { reportDao.getAll(any()) } returns reportList

        val news = runBlocking { newsLocalDataSource.getNews(PAGE_ONE) }

        coVerify { reportDao.getAll(any()) }
        assertEquals(news, reportList)
    }

    @Test
    fun `get report by id should returns the report`() {
        val report = ReportDtoBuilder.build().toEntity()
        coEvery { reportDao.findBy(any()) } returns report

        val result = runBlocking { newsLocalDataSource.getReport(REPORT_ID) }

        coVerify { reportDao.findBy(any()) }
        assertEquals(report, result)
    }

    @Test
    fun `save report should store the report in db`() {
        runBlocking { newsLocalDataSource.save(ReportDtoBuilder.build().toEntity()) }

        val slot = slot<ReportEntity>()

        coVerify { reportDao.insert(capture(slot)) }
    }

    @Test
    fun `remove all should remove all reports`() {
        runBlocking { newsLocalDataSource.removeAll() }

        coVerify { reportDao.deleteAll() }
    }
}