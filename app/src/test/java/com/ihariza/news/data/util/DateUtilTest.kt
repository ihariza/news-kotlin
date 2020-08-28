package com.ihariza.news.data.util

import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class DateUtilTest {

    @Test
    fun `given timestamp should return time in long format`() {
        val result = DateUtil.getLongTime("2020-08-26 08:28:42 +0000")

        assert(result == 1598423322000L)
    }

    @Test
    fun `given an invalid timestamp should return 0`() {
        val result = DateUtil.getLongTime("2020-08-26T08:28:42 +0000")

        assert(result == 0L)
    }

    @Test
    fun `given a null timestamp should return 0`() {
        val result = DateUtil.getLongTime(null)

        assert(result == 0L)
    }

    @Test
    fun `given a 0 number of days before current time should return current date in RFC 3339 format`() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss +0000", Locale.getDefault())
        val currentDate: String = dateFormat.format(Date())

        val result: String = DateUtil.getRFC3339Time(0) ?: ""

        assert(result == currentDate)
    }

}