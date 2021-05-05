package com.nhariza.news.view.report

import androidx.lifecycle.Observer
import com.nhariza.news.builder.ReportDtoBuilder
import com.nhariza.news.repository.NewsRepository
import com.nhariza.news.repository.model.Report
import com.nhariza.news.repository.toEntity
import com.nhariza.news.repository.toModel
import com.nhariza.news.view.base.BaseViewModelTest
import com.nhariza.news.view.util.DateFormatter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.net.UnknownHostException


@ExperimentalCoroutinesApi
class ReportViewModelTest : BaseViewModelTest() {

    companion object {
        private const val REPORT_ID = "1"
    }

    @MockK
    lateinit var newsRepository: NewsRepository

    @MockK
    lateinit var reportViewModel: ReportViewModel

    @SpyK
    val reportEventObserver: Observer<Report?> = spyk(Observer { })

    override fun setup() {
        reportViewModel = ReportViewModel(newsRepository)
        reportViewModel.report.observeForever(reportEventObserver)
    }

    override fun tearDown() {
        reportViewModel.report.removeObserver(reportEventObserver)
    }

    @Test
    fun `given a report id should show report detail`() {
        showReport()

        coVerify {
            newsRepository.getReport(any())
        }

        assertEquals(
            ReportDtoBuilder.build().toEntity().toModel(),
            reportViewModel.report.value
        )
    }

    @Test
    fun `given a null report shouldn't show report detail`() {
        coEvery {
            newsRepository.getReport(any())
        } returns null

        reportViewModel.showReport(REPORT_ID)

        coVerify {
            newsRepository.getReport(any())
        }
        assertNull(reportViewModel.report.value)
    }

    @Test
    fun `given an exception on show report should show message error`() {
        val errorEventObserver: Observer<String> = spyk(Observer { })
        reportViewModel.errorEvent.observeForever(errorEventObserver)

        coEvery {
            newsRepository.getReport(any())
        } throws UnknownHostException()

        reportViewModel.showReport(REPORT_ID)

        val errorSlot = slot<String>()

        coVerifySequence {
            newsRepository.getReport(any())
            errorEventObserver.onChanged(capture(errorSlot))
        }
    }

    @Test
    fun `share report should modify share report event value`() {
        showReport()
        reportViewModel.shareReport()

        assertEquals(
            ReportDtoBuilder.build().toEntity().toModel(),
            reportViewModel.shareReportEvent.value
        )
    }

    @Test
    fun `given null report shouldn't share report`() {
        reportViewModel.shareReport()

        assertNull(reportViewModel.report.value)
    }

    @Test
    fun `open web report should modify open report event value`() {
        showReport()
        reportViewModel.openWebReport()

        assertEquals(
            ReportDtoBuilder.build().toEntity().toModel().url,
            reportViewModel.openWebReportEvent.value
        )
    }

    @Test
    fun `given null report shouldn't open report web`() {
        reportViewModel.openWebReport()

        assert(reportViewModel.report.value == null)
    }

    private fun showReport() {
        mockkObject(DateFormatter)

        coEvery {
            DateFormatter.getRelativeTime(any())
        } returns "5 hour ago"

        coEvery {
            newsRepository.getReport(any())
        } returns ReportDtoBuilder.build().toEntity().toModel()

        reportViewModel.showReport(REPORT_ID)

        val reportSlot = slot<Report>()

        coVerify {
            reportEventObserver.onChanged(capture(reportSlot))
        }
    }

}