package com.ihariza.news.presentation.report

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ihariza.news.domain.usecase.GetReportUseCase
import com.ihariza.news.fake.FakeNewsTest
import com.ihariza.news.presentation.event.Event
import com.ihariza.news.presentation.model.toVm
import com.ihariza.news.presentation.view.report.ReportViewModel
import com.ihariza.news.presentation.view.util.DateUtils
import com.ihariza.news.rules.TestCoroutineRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException


@ExperimentalCoroutinesApi
class ReportViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    lateinit var getReportUseCase: GetReportUseCase

    @MockK lateinit var reportViewModel: ReportViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        reportViewModel = ReportViewModel(testCoroutineRule.testCoroutineDispatcher, getReportUseCase)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `given a report id should show report detail`() = testCoroutineRule.runBlockingTest {
        showReport()

        coVerify {
            getReportUseCase.getReport(any())
        }

        assert(reportViewModel.report.value == FakeNewsTest.getReportBo().toVm())
    }

    @Test
    fun `given a null report shouldn't show report detail`() = testCoroutineRule.runBlockingTest {
        coEvery {
            getReportUseCase.getReport(any())
        } returns null

        reportViewModel.showReport(FakeNewsTest.REPORT_ID)

        coVerify {
            getReportUseCase.getReport(any())
        }
        assert(reportViewModel.report.value == null)
    }

    @Test
    fun `given an exception on show report should show message error`() = testCoroutineRule.runBlockingTest {
        val errorEventObserver: Observer<Event<String>> = spyk(Observer {  })
        reportViewModel.errorEvent.observeForever(errorEventObserver)

        coEvery {
            getReportUseCase.getReport(any())
        } throws UnknownHostException()

        reportViewModel.showReport(FakeNewsTest.REPORT_ID)

        val errorSlot = slot<Event<String>>()

        coVerifySequence {
            getReportUseCase.getReport(any())
            errorEventObserver.onChanged(capture(errorSlot))
        }
    }

    @Test
    fun `share report should modify share report event value`() = testCoroutineRule.runBlockingTest {
        showReport()
        reportViewModel.shareReport()

        assert(reportViewModel.shareReportEvent.value?.peekContent()
                == FakeNewsTest.getReportBo().toVm())
    }

    @Test
    fun `given null report shouldn't share report`() {
        reportViewModel.shareReport()

        assert(reportViewModel.report.value == null)
    }

    @Test
    fun `open web report should modify open report event value`() = testCoroutineRule.runBlockingTest {
        showReport()
        reportViewModel.openWebReport()

        assert(reportViewModel.openWebReportEvent.value?.peekContent()
                == FakeNewsTest.getReportBo().toVm().url)
    }

    @Test
    fun `given null report shouldn't open report web`() {
        reportViewModel.openWebReport()

        assert(reportViewModel.report.value == null)
    }

    private fun showReport() {
        mockkObject(DateUtils)

        coEvery {
            DateUtils.getRelativeTime(any())
        } returns "5 hour ago"

        coEvery {
            getReportUseCase.getReport(any())
        } returns FakeNewsTest.getReportBo()

        reportViewModel.showReport(FakeNewsTest.REPORT_ID)
    }

}