package com.ihariza.news.domain

import com.ihariza.news.domain.repository.NewsRepository
import com.ihariza.news.domain.usecase.GetReportUseCase
import com.ihariza.news.fake.FakeNewsTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetReportUseCaseTest {

    @MockK
    lateinit var newsRepository: NewsRepository

    private lateinit var getReportUseCase: GetReportUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getReportUseCase = GetReportUseCase(newsRepository)
    }

    @Test
    fun `given a report id should return the report`() {
        coEvery {
            newsRepository.getReport(any())
        } returns FakeNewsTest.getReportBo()

        val report = runBlocking { getReportUseCase.getReport("") }

        assert(report == FakeNewsTest.getReportBo())

        coVerify { getReportUseCase.getReport(any()) }
    }
}