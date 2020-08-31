package com.ihariza.news.presentation.view.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ihariza.news.domain.usecase.GetNewsUseCase
import com.ihariza.news.presentation.event.Event
import com.ihariza.news.presentation.model.Report
import com.ihariza.news.presentation.model.toVm
import com.ihariza.news.presentation.view.base.BaseViewModel
import com.ihariza.news.presentation.view.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(private val getNewsUseCase: GetNewsUseCase) : BaseViewModel() {

    private val _news = MutableLiveData<List<Report>>()
    val news: LiveData<List<Report>> get() = _news

    private val _lastPageEvent = MutableLiveData<Event<Boolean>>()
    val lastPageEvent: LiveData<Event<Boolean>> get() = _lastPageEvent

    private val _loadingEvent = MutableLiveData<Event<Boolean>>()
    val loadingEvent: LiveData<Event<Boolean>> get() = _loadingEvent

    private val _refreshingEvent = MutableLiveData<Event<Boolean>>()
    val refreshingEvent: LiveData<Event<Boolean>> get() = _refreshingEvent

    private val _openReportEvent = MutableLiveData<Event<String>>()
    val openReportEvent: LiveData<Event<String>> get() = _openReportEvent

    fun getNewsPage(pageNumber: Int) {
        _loadingEvent.value = Event(true)
        getNews(pageNumber)
    }

    fun refreshNews() {
        getNews(Constants.START_NEWS_PAGE)
    }

    fun openReport(reportId: String) {
        _openReportEvent.value = Event(reportId)
    }

    private fun getNews(pageNumber: Int) {
        viewModelScope.launch {
            try {
                _news.value = withContext(Dispatchers.IO) {
                    getNewsUseCase.getNews(pageNumber).map { it.toVm() }
                }
                hideLoading()
            } catch (e: Exception) {
                hideLoading()
                mErrorEvent.value = Event(e.message.toString())
            }
        }
    }

    private fun hideLoading() {
        _refreshingEvent.value = Event(false)
        _loadingEvent.value = Event(false)
    }
}