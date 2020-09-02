package com.ihariza.news.presentation.view.main

import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.ihariza.news.BuildConfig
import com.ihariza.news.NewsApplication
import com.ihariza.news.R
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class MainActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        val appContext = ApplicationProvider.getApplicationContext<NewsApplication>()
        assertEquals(BuildConfig.APPLICATION_ID, appContext.packageName)
    }

    @Test
    fun appLaunchesSuccessfully() {
        launchActivity<MainActivity>()
    }

    @Test
    fun mainShowsNewsFragmentAtStart() {
        onView(withId(R.id.newsFragmentContainer)).check(matches(isDisplayed()))
    }
}