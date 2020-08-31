package com.ihariza.news.presentation.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ihariza.news.presentation.event.Event

open class BaseViewModel : ViewModel() {

    protected val mErrorEvent = MutableLiveData<Event<String>>()
    val errorEvent: LiveData<Event<String>> get() = mErrorEvent

}