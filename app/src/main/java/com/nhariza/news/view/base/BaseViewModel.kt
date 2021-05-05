package com.nhariza.news.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    protected val mErrorEvent = MutableLiveData<String>()
    val errorEvent: LiveData<String> get() = mErrorEvent

    fun ViewModel.executeInBackground(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            block.invoke(viewModelScope)
        }
    }
}