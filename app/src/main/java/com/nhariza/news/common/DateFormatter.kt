package com.nhariza.news.common

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    private const val TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss +0000"

    /**
     * Transform timestamp into long time
     *
     * @param timestamp yyyy-MM-dd HH:mm:ss +0000
     * @return date in long format
     */
    fun getLongTime(timestamp: String?): Long {
        timestamp?.let {
            return try {
                val inputFormat = SimpleDateFormat(TIMESTAMP_FORMAT, Locale.getDefault())
                inputFormat.parse(timestamp)?.time ?: 0
            } catch (e: ParseException) {
                return 0
            }
        }
        return 0
    }

    /**
     * Gets time given a number of days before current time
     *
     * @param daysBeforeNow number of days before current time
     * @return date in RFC 3339 format
     */
    fun getRFC3339Time(daysBeforeNow: Int): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val daysBeforeNowMillis = 1000 * 60 * 60 * 24 * daysBeforeNow
        return inputFormat.format(System.currentTimeMillis() - daysBeforeNowMillis)
    }
}