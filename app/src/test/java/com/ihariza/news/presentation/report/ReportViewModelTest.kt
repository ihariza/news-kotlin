package com.ihariza.news.presentation.report

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ihariza.news.domain.usecase.GetReportUseCase
import com.ihariza.news.fake.FakeNewsTest
import com.ihariza.news.presentation.event.Event
import com.ihariza.news.presentation.model.Report
import com.ihariza.news.presentation.model.toVm
import com.ihariza.news.presentation.view.report.ReportViewModel
import com.ihariza.news.presentation.view.util.DateUtils
import com.ihariza.news.rules.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ReportViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    lateinit var dateUtils: DateUtils

    @MockK
    lateinit var getReportUseCase: GetReportUseCase

    @SpyK
    val shareReportEventObserver: Observer<Event<Report>> = spyk(Observer { })

    @SpyK
    val openWebReportObserver: Observer<Event<String>> = spyk(Observer { })

    @MockK lateinit var reportViewModel: ReportViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        reportViewModel = ReportViewModel(getReportUseCase)
        reportViewModel.shareReportEvent.observeForever(shareReportEventObserver)
        reportViewModel.openWebReportEvent.observeForever(openWebReportObserver)
    }

    @Test
    fun `given a report id should show report detail`() = testCoroutineRule.runBlockingTest {
        coEvery {
            getReportUseCase.getReport(any())
        } returns FakeNewsTest.getReportBo()

        reportViewModel.showReport(FakeNewsTest.REPORT_ID)

        coVerify { getReportUseCase.getReport(any()) }

        reportViewModel.report.observeForever {
            assert(it == FakeNewsTest.getReportBo().toVm())
        }
    }

    @Test
    fun `share report should modify share report event value`() = testCoroutineRule.runBlockingTest {
        coEvery {
            dateUtils.getRelativeTimeSpanString(any())
        } returns "5 hour ago"

        coEvery {
            getReportUseCase.getReport(any())
        } returns FakeNewsTest.getReportBo()

        reportViewModel.showReport(FakeNewsTest.REPORT_ID)
        reportViewModel.shareReport()

        reportViewModel.shareReportEvent.observeForever {
            assert(it.peekContent() == FakeNewsTest.getReportBo().toVm())
        }
    }

    @Test
    fun `open web report should modify open report event value`() = testCoroutineRule.runBlockingTest {
        coEvery {
            dateUtils.getRelativeTimeSpanString(any())
        } returns "5 hour ago"

        coEvery {
            getReportUseCase.getReport(any())
        } returns FakeNewsTest.getReportBo()

        reportViewModel.showReport(FakeNewsTest.REPORT_ID)
        reportViewModel.openWebReport()

        reportViewModel.openWebReportEvent.observeForever{
            assert(it.peekContent() == FakeNewsTest.getReportBo().toVm().url)
        }
    }

}