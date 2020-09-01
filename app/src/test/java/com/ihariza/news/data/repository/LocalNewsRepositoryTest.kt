package com.ihariza.news.data.repository

import com.ihariza.news.data.api.dto.toEntity
import com.ihariza.news.data.database.ReportDao
import com.ihariza.news.data.entity.ReportEntity
import com.ihariza.news.data.repository.local.LocalNewsRepository
import com.ihariza.news.data.repository.local.LocalNewsRepositoryImp
import com.ihariza.news.fake.FakeNewsTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LocalNewsRepositoryTest {

    @RelaxedMockK
    lateinit var reportDao: ReportDao

    private lateinit var localNewsRepository: LocalNewsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        localNewsRepository = LocalNewsRepositoryImp(reportDao)
    }

    @Test
    fun `get news should returns a list of news`() {
        coEvery {
            reportDao.getAll(any())
        } returns FakeNewsTest.getReportDtoList().toEntity()

        val news = runBlocking { localNewsRepository.getNews(FakeNewsTest.PAGE_ONE) }

        coVerify {
            reportDao.getAll(any())
        }
        assert(news == FakeNewsTest.getReportDtoList().toEntity())
    }

    @Test
    fun `get report by id should returns the report`() {
        coEvery {
            reportDao.findBy(any())
        } returns FakeNewsTest.getReportDto().toEntity()

        val report = runBlocking { localNewsRepository.getReport(FakeNewsTest.REPORT_ID) }

        coVerify {
            reportDao.findBy(any())
        }
        assert(report == FakeNewsTest.getReportDto().toEntity())
    }

    @Test
    fun `save report should store the report in db`() {
        runBlocking { localNewsRepository.save(FakeNewsTest.getReportDto().toEntity()) }

        val slot = slot<ReportEntity>()

        coVerify { reportDao.insert(capture(slot)) }
    }

    @Test
    fun `remove all should remove all reports`() {
        runBlocking { localNewsRepository.removeAll() }

        coVerify { reportDao.deleteAll() }
    }
}