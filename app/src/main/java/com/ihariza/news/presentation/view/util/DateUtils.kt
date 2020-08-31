package com.ihariza.news.presentation.view.util

import android.text.format.DateUtils

object DateUtils {

    fun getRelativeTimeSpanString(startTime: Long): String {
        return DateUtils.getRelativeTimeSpanString(startTime).toString()
    }
}