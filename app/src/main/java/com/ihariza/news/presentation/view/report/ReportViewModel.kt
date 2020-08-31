package com.ihariza.news.presentation.view.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ihariza.news.domain.usecase.GetReportUseCase
import com.ihariza.news.presentation.event.Event
import com.ihariza.news.presentation.model.Report
import com.ihariza.news.presentation.model.toVm
import com.ihariza.news.presentation.view.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReportViewModel(
        private val ioDispatcher: CoroutineDispatcher,
        private val getReportUseCase: GetReportUseCase) : BaseViewModel() {

    private val _report = MutableLiveData<Report?>()
    val report: LiveData<Report?> get() = _report

    private val _shareReportEvent = MutableLiveData<Event<Report>>()
    val shareReportEvent: LiveData<Event<Report>> get() = _shareReportEvent

    private val _openWebReportEvent = MutableLiveData<Event<String>>()
    val openWebReportEvent: LiveData<Event<String>> get() = _openWebReportEvent

    fun showReport(reportId: String) {
        viewModelScope.launch {
            try {
                _report.value = withContext(ioDispatcher) {
                    getReportUseCase.getReport(reportId)?.toVm()
                }
            } catch (e: Exception) {
                mErrorEvent.value = Event(e.message.toString())
            }
        }
    }

    fun shareReport() {
        _report.value?.let {  _shareReportEvent.value = Event(it) }
    }

    fun openWebReport() {
        _report.value?.url?.let { _openWebReportEvent.value = Event(it) }
    }
}