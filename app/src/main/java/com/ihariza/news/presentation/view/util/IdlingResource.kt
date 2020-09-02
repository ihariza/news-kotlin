package com.ihariza.news.presentation.view.util

import androidx.test.espresso.idling.CountingIdlingResource

object IdlingResource  {

    val countingIdlingResource: CountingIdlingResource =
            CountingIdlingResource("GLOBAL")

    fun increment() {
        if (countingIdlingResource.isIdleNow) {
            countingIdlingResource.increment()
        }
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}