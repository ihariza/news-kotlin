package com.ihariza.news.presentation.view.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ihariza.news.domain.usecase.GetReportUseCase
import com.ihariza.news.presentation.event.Event
import com.ihariza.news.presentation.model.Report
import com.ihariza.news.presentation.model.toVm
import com.ihariza.news.presentation.view.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ReportViewModel(private val getReportUseCase: GetReportUseCase) : BaseViewModel() {

    private val _report= MutableLiveData<Report?>()
    val report: LiveData<Report?> get() = _report

    private val _shareReportEvent = MutableLiveData<Event<Report>>()
    val shareReportEvent: LiveData<Event<Report>> get() = _shareReportEvent

    private val _openWebReportEvent = MutableLiveData<Event<String>>()
    val openWebReportEvent: LiveData<Event<String>> get() = _openWebReportEvent

    fun showReport(reportId: String) {
        launch {
            try {
                _report.value = withContext(Dispatchers.IO) {
                    getReportUseCase.getReport(reportId)?.toVm()
                }
            } catch (e: Exception) {
                mErrorEvent.value = Event(e.message.toString())
            }
        }
    }

    fun shareReport() {
        report.value?.let {  _shareReportEvent.value = Event(it) }
    }

    fun openWebReport() {
        report.value?.url?.let {  _openWebReportEvent.value = Event(it) }
    }
}