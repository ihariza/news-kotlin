package com.nhariza.news.view.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nhariza.news.repository.NewsRepository
import com.nhariza.news.repository.model.Report
import com.nhariza.news.view.base.BaseViewModel
import com.nhariza.news.view.base.Event
import com.nhariza.news.view.util.Constants

class NewsViewModel(private val newsRepository: NewsRepository) : BaseViewModel() {

    private val _news = MutableLiveData<List<Report>>()
    val news: LiveData<List<Report>> get() = _news

    private val _lastPageEvent = MutableLiveData<Event<Boolean>>()
    val lastPageEvent: LiveData<Event<Boolean>> get() = _lastPageEvent

    private val _refreshingEvent = MutableLiveData<Event<Boolean>>()
    val refreshingEvent: LiveData<Event<Boolean>> get() = _refreshingEvent

    private val _openReportEvent = MutableLiveData<Event<String>>()
    val openReportEvent: LiveData<Event<String>> get() = _openReportEvent

    fun getNewsPage(pageNumber: Int) {
        getNews(pageNumber)
    }

    fun refreshNews() {
        getNews(Constants.START_NEWS_PAGE)
    }

    fun openReport(reportId: String) {
        _openReportEvent.value = Event(reportId)
    }

    private fun getNews(pageNumber: Int) {
        executeInBackground {
            try {
                newsRepository.getNews(pageNumber)?.let {
                    _news.postValue(it)
                }
                hideLoading()
            } catch (e: Exception) {
                hideLoading()
                mErrorEvent.postValue(Event(e.message.toString()))
            }
        }
    }

    private fun hideLoading() {
        _refreshingEvent.postValue(Event(false))
    }
}