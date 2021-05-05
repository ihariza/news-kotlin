package com.nhariza.news.view.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nhariza.news.repository.NewsRepository
import com.nhariza.news.repository.model.Report
import com.nhariza.news.view.base.BaseViewModel
import com.nhariza.news.view.base.Event

class ReportViewModel(private val newsRepository: NewsRepository) : BaseViewModel() {

    private val _report = MutableLiveData<Report?>()
    val report: LiveData<Report?> get() = _report

    private val _shareReportEvent = MutableLiveData<Event<Report>>()
    val shareReportEvent: LiveData<Event<Report>> get() = _shareReportEvent

    private val _openWebReportEvent = MutableLiveData<Event<String>>()
    val openWebReportEvent: LiveData<Event<String>> get() = _openWebReportEvent

    fun showReport(reportId: String) {
        executeInBackground {
            try {
                _report.postValue(newsRepository.getReport(reportId))
            } catch (e: Exception) {
                mErrorEvent.postValue(Event(e.message.toString()))
            }
        }
    }

    fun shareReport() {
        report.value?.let { _shareReportEvent.value = Event(it) }
    }

    fun openWebReport() {
        report.value?.url?.let { _openWebReportEvent.value = Event(it) }
    }
}