package com.nhariza.news.repository.datasource

import com.nhariza.news.builder.NewsDtoBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NewsRemoteDataSourceTest {

    companion object {
        private const val PAGE_ONE = 1
    }

    @RelaxedMockK
    lateinit var newsService: NewsService

    private lateinit var newsRemoteDataSource: NewsRemoteDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        newsRemoteDataSource = NewsRemoteDataSource(newsService)
    }

    @Test
    fun `get news should returns a newsDto`() {
        val news =  NewsDtoBuilder.build()
        coEvery {
            newsService.getNews(any(), any(), any(), any(), any())
        } returns news

        val result = runBlocking { newsRemoteDataSource.getNews(PAGE_ONE) }

        coVerify { newsService.getNews(any(), any(), any(), any(), any()) }
        assertEquals(result, news)
    }
}