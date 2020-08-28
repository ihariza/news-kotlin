package com.ihariza.news.presentation.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ihariza.news.domain.usecase.GetNewsUseCase
import com.ihariza.news.fake.FakeNewsTest
import com.ihariza.news.presentation.event.Event
import com.ihariza.news.presentation.view.news.NewsViewModel
import com.ihariza.news.rules.TestCoroutineRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class NewsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    lateinit var getNewsUseCase: GetNewsUseCase

    @SpyK
    val errorEventObserver: Observer<Event<String>> = spyk(Observer {  })

    @SpyK
    val loadingEventObserver: Observer<Event<Boolean>> = spyk(Observer {  })

    @SpyK
    val refreshEventObserver: Observer<Event<Boolean>> = spyk(Observer {  })

    private lateinit var newsViewModel: NewsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        newsViewModel = NewsViewModel(getNewsUseCase)
        newsViewModel.errorEvent.observeForever(errorEventObserver)
        newsViewModel.loadingEvent.observeForever(loadingEventObserver)
        newsViewModel.refreshingEvent.observeForever(refreshEventObserver)
    }

    @Test
    fun `given page number should show a list of report`() = runBlocking {
        coEvery {
            getNewsUseCase.getNews(any())
        } returns FakeNewsTest.getNewsBo()

        newsViewModel.getNewsPage(FakeNewsTest.PAGE_ONE)

        val loadingSlot = slot<Event<Boolean>>()
        val refreshSlot = slot<Event<Boolean>>()

        coVerifySequence {
            loadingEventObserver.onChanged(capture(loadingSlot))
            getNewsUseCase.getNews(any())
            refreshEventObserver.onChanged(capture(refreshSlot))
            loadingEventObserver.onChanged(capture(loadingSlot))
        }
    }

    @Test
    fun `given an error should show show message error`() = runBlocking {
        coEvery {
            getNewsUseCase.getNews(any())
        } throws UnknownHostException()

        newsViewModel.getNewsPage(FakeNewsTest.PAGE_ONE)

        val loadingSlot = slot<Event<Boolean>>()
        val refreshSlot = slot<Event<Boolean>>()
        val errorSlot = slot<Event<String>>()

        coVerifyOrder {
            loadingEventObserver.onChanged(capture(loadingSlot))
            getNewsUseCase.getNews(any())
            refreshEventObserver.onChanged(capture(refreshSlot))
            loadingEventObserver.onChanged(capture(loadingSlot))
            errorEventObserver.onChanged(capture(errorSlot))
        }
    }

    @Test
    fun `refresh news should clean and show list of report`() = runBlocking {
        coEvery {
            getNewsUseCase.getNews(any())
        } returns FakeNewsTest.getNewsBo()

        newsViewModel.refreshNews()

        val loadingSlot = slot<Event<Boolean>>()
        val refreshSlot = slot<Event<Boolean>>()

        coVerifySequence {
            getNewsUseCase.getNews(any())
            refreshEventObserver.onChanged(capture(refreshSlot))
            loadingEventObserver.onChanged(capture(loadingSlot))
        }
    }

    @Test
    fun `open report should show report detail`()  {

    }
}