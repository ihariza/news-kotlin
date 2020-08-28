package com.ihariza.news.presentation.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ihariza.news.presentation.event.Event
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {

    private val job: Job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    protected val mErrorEvent = MutableLiveData<Event<String>>()
    val errorEvent: LiveData<Event<String>> get() = mErrorEvent

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}