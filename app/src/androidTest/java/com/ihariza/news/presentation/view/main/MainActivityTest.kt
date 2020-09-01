package com.ihariza.news.presentation.view.main

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.ihariza.news.BuildConfig
import com.ihariza.news.NewsApplication
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        val appContext = ApplicationProvider.getApplicationContext<NewsApplication>()
        assertEquals(BuildConfig.APPLICATION_ID, appContext.packageName)
    }

    @Test
    fun appLaunchesSuccessfully() {
        ActivityScenario.launch(MainActivity::class.java)
    }
}