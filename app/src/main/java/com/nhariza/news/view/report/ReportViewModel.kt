package com.nhariza.news.view.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nhariza.news.repository.NewsRepository
import com.nhariza.news.repository.model.Report
import com.nhariza.news.view.base.BaseViewModel

class ReportViewModel(private val newsRepository: NewsRepository) : BaseViewModel() {

    private val _report = MutableLiveData<Report?>()
    val report: LiveData<Report?> get() = _report

    private val _shareReportEvent = MutableLiveData<Report>()
    val shareReportEvent: LiveData<Report> get() = _shareReportEvent

    private val _openWebReportEvent = MutableLiveData<String>()
    val openWebReportEvent: LiveData<String> get() = _openWebReportEvent

    fun showReport(reportId: String) {
        executeInBackground {
            try {
                _report.postValue(newsRepository.getReport(reportId))
            } catch (e: Exception) {
                mErrorEvent.postValue(e.message.toString())
            }
        }
    }

    fun shareReport() {
        report.value?.let { _shareReportEvent.value = it }
    }

    fun openWebReport() {
        report.value?.url?.let { _openWebReportEvent.value = it }
    }
}