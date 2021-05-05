package com.nhariza.news.common

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*


class DateFormatterTest {

    @Test
    fun `given timestamp should return time in long format`() {
        val result = DateFormatter.getLongTime("2020-08-26 08:28:42 +0000")

        assertEquals(result, 1598423322000L)
    }

    @Test
    fun `given an invalid timestamp should return 0`() {
        val result = DateFormatter.getLongTime("2020-08-26T08:28:42 +0000")

        assertEquals(result, 0L)
    }

    @Test
    fun `given a null timestamp should return 0`() {
        val result = DateFormatter.getLongTime(null)

        assertEquals(result, 0L)
    }

    @Test
    fun `given a 0 number of days before current time should return current date in RFC 3339 format`() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate: String = dateFormat.format(Date())

        val result = DateFormatter.getRFC3339Time(0)

        assertEquals(result, currentDate)
    }
}