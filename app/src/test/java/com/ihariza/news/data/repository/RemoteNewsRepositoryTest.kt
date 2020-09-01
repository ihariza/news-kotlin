package com.ihariza.news.data.repository

import com.ihariza.news.data.api.NewsApi
import com.ihariza.news.data.api.dto.toEntity
import com.ihariza.news.data.repository.remote.RemoteNewsRepository
import com.ihariza.news.data.repository.remote.RemoteNewsRepositoryImp
import com.ihariza.news.fake.FakeNewsTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RemoteNewsRepositoryTest {

    @RelaxedMockK
    lateinit var newsApi: NewsApi

    private lateinit var remoteNewsRepository: RemoteNewsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        remoteNewsRepository = RemoteNewsRepositoryImp(newsApi)
    }

    @Test
    fun `get news should returns a newsDto`() {
        coEvery {
            newsApi.getNews(any(), any(), any(), any(), any())
        } returns FakeNewsTest.getNewsDto()

        val news = runBlocking { remoteNewsRepository.getNews(FakeNewsTest.PAGE_ONE) }

        coVerify { newsApi.getNews(any(), any(), any(), any(), any()) }
        assert(news == FakeNewsTest.getReportDtoList().toEntity())
    }
}