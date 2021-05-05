package com.nhariza.news.view.news

import androidx.lifecycle.Observer
import com.nhariza.news.builder.ReportDtoBuilder
import com.nhariza.news.repository.NewsRepository
import com.nhariza.news.repository.toEntity
import com.nhariza.news.repository.toModel
import com.nhariza.news.view.base.BaseViewModelTest
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class NewsViewModelTest : BaseViewModelTest() {

    companion object {
        private const val PAGE_ONE = 1
        private const val REPORT_ID = "1"
    }

    @RelaxedMockK
    lateinit var newsRepository: NewsRepository

    @SpyK
    val refreshEventObserver: Observer<Boolean> = spyk(Observer { })

    private lateinit var newsViewModel: NewsViewModel


    override fun setup() {
        newsViewModel = NewsViewModel(newsRepository)
        newsViewModel.refreshingEvent.observeForever(refreshEventObserver)
    }

    override fun tearDown() {
        newsViewModel.refreshingEvent.removeObserver(refreshEventObserver)
    }

    @Test
    fun `given page number should show a list of report`() {
        coEvery {
            newsRepository.getNews(any())
        } returns ReportDtoBuilder.buildDtoList().toEntity().toModel()

        newsViewModel.getNewsPage(PAGE_ONE)

        val refreshSlot = slot<Boolean>()

        coVerifySequence {
            newsRepository.getNews(any())
            refreshEventObserver.onChanged(capture(refreshSlot))
        }
    }

    @Test
    fun `given an error should show show message error`() {
        val errorEventObserver: Observer<String> = spyk(Observer { })
        newsViewModel.errorEvent.observeForever(errorEventObserver)

        coEvery {
            newsRepository.getNews(any())
        } throws UnknownHostException()

        newsViewModel.getNewsPage(PAGE_ONE)

        val refreshSlot = slot<Boolean>()
        val errorSlot = slot<String>()

        coVerifySequence {
            newsRepository.getNews(any())
            refreshEventObserver.onChanged(capture(refreshSlot))
            errorEventObserver.onChanged(capture(errorSlot))
        }
    }

    @Test
    fun `refresh news should clean and show list of report`() {
        coEvery {
            newsRepository.getNews(any())
        } returns ReportDtoBuilder.buildDtoList().toEntity().toModel()

        newsViewModel.refreshNews()

        coVerify { newsRepository.getNews(any()) }
    }

    @Test
    fun `open report should show report detail`() {
        val openReportEventObserver: Observer<String?> = spyk(Observer { })
        newsViewModel.openReportEvent.observeForever(openReportEventObserver)

        newsViewModel.openReport(REPORT_ID)

        verifySequence {
            openReportEventObserver.onChanged(REPORT_ID)
        }
    }
}