package com.nhariza.news.view.util

import android.text.format.DateUtils

object DateFormatter {

    fun getRelativeTime(startTime: Long): String {
        return DateUtils.getRelativeTimeSpanString(startTime).toString()
    }
}