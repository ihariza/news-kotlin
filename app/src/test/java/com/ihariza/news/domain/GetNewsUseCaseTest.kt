package com.ihariza.news.domain

import com.ihariza.news.domain.repository.NewsRepository
import com.ihariza.news.domain.usecase.GetNewsUseCase
import com.ihariza.news.fake.FakeNewsTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNewsUseCaseTest {

    @MockK
    lateinit var newsRepository: NewsRepository

    private lateinit var getNewsUseCase: GetNewsUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getNewsUseCase = GetNewsUseCase(newsRepository)
    }

    @Test
    fun `given a page number should return a list of report order by date`() {
        coEvery{
            newsRepository.getNews(any())
        } returns FakeNewsTest.getReportBoList()

        val news = runBlocking { getNewsUseCase.getNews(FakeNewsTest.PAGE_ONE) }

        assert(news == FakeNewsTest.getReportBoList())

        coVerify { newsRepository.getNews(any()) }
    }
}