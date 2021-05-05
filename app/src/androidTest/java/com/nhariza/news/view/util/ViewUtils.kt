package com.nhariza.news.view.util

import androidx.test.platform.app.InstrumentationRegistry


class ViewUtils {

    companion object {
        fun waitForIdle(sleep: SLEEP) {
            Thread.sleep(sleep.millis)
            InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        }
    }
}

enum class SLEEP(val millis: Long) {
    NONE(50),
    LITTLE(100),
    MEDIUM(500),
    LARGE(1500)
}