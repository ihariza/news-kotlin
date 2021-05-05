package com.nhariza.news.view.news

import androidx.lifecycle.Observer
import com.nhariza.news.builder.ReportDtoBuilder
import com.nhariza.news.repository.NewsRepository
import com.nhariza.news.repository.toEntity
import com.nhariza.news.repository.toModel
import com.nhariza.news.view.base.BaseViewModelTest
import com.nhariza.news.view.base.Event
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
    val loadingEventObserver: Observer<Event<Boolean>> = spyk(Observer { })

    @SpyK
    val refreshEventObserver: Observer<Event<Boolean>> = spyk(Observer { })

    private lateinit var newsViewModel: NewsViewModel


    override fun setup() {
        newsViewModel = NewsViewModel(newsRepository)
        newsViewModel.loadingEvent.observeForever(loadingEventObserver)
        newsViewModel.refreshingEvent.observeForever(refreshEventObserver)
    }

    override fun tearDown() {
        newsViewModel.loadingEvent.removeObserver(loadingEventObserver)
        newsViewModel.refreshingEvent.removeObserver(refreshEventObserver)
    }

    @Test
    fun `given page number should show a list of report`() {
        coEvery {
            newsRepository.getNews(any())
        } returns ReportDtoBuilder.buildDtoList().toEntity().toModel()

        newsViewModel.getNewsPage(PAGE_ONE)

        val loadingSlot = slot<Event<Boolean>>()
        val refreshSlot = slot<Event<Boolean>>()

        coVerifySequence {
            loadingEventObserver.onChanged(capture(loadingSlot))
            newsRepository.getNews(any())
            refreshEventObserver.onChanged(capture(refreshSlot))
            loadingEventObserver.onChanged(capture(loadingSlot))
        }
    }

    @Test
    fun `given an error should show show message error`() {
        val errorEventObserver: Observer<Event<String>> = spyk(Observer { })
        newsViewModel.errorEvent.observeForever(errorEventObserver)

        coEvery {
            newsRepository.getNews(any())
        } throws UnknownHostException()

        newsViewModel.getNewsPage(PAGE_ONE)

        val loadingSlot = slot<Event<Boolean>>()
        val refreshSlot = slot<Event<Boolean>>()
        val errorSlot = slot<Event<String>>()

        coVerifySequence {
            loadingEventObserver.onChanged(capture(loadingSlot))
            newsRepository.getNews(any())
            refreshEventObserver.onChanged(capture(refreshSlot))
            loadingEventObserver.onChanged(capture(loadingSlot))
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
        val openReportEventObserver: Observer<Event<String>> = spyk(Observer { })
        newsViewModel.openReportEvent.observeForever(openReportEventObserver)

        newsViewModel.openReport(REPORT_ID)

        val openReportSlot = slot<Event<String>>()

        verify { openReportEventObserver.onChanged(capture(openReportSlot)) }
    }
}