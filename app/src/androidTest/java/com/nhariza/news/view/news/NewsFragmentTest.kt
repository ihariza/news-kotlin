package com.nhariza.news.view.news

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.nhariza.news.R
import com.nhariza.news.view.base.BaseAndroidTest
import com.nhariza.news.view.news.adapter.ReportViewHolder
import com.nhariza.news.view.util.SLEEP
import com.nhariza.news.view.util.ViewUtils.Companion.waitForIdle
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import io.mockk.mockk
import org.hamcrest.Matchers
import org.junit.Test


class NewsFragmentTest : BaseAndroidTest() {

    private lateinit var scenario: FragmentScenario<NewsFragment>

    override fun setup() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_MaterialComponents_DayNight_NoActionBar)
    }

    @Test
    fun onStartShouldShowRecyclerViewOnFragment() {
        Espresso.onView(withId(R.id.recyclerview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun navigationToReportDetail() {
        val mockNavController = mockk<NavController>(relaxed = true)
        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), mockNavController)
        }

        Espresso.onView(withId(R.id.recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ReportViewHolder>(0, ViewActions.click())
        )

        Espresso.onView(
            Matchers.allOf(
                withId(R.id.action_share),
                withId(R.id.action_share),
                ViewMatchers.isDescendantOfA(withId(R.id.toolbar))
            )
        )
    }

    @Test
    fun showRefreshLoading() {
        Espresso.onView(withId(R.id.recyclerview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        scenario.onFragment {
            it.binding.swipeRefresh.isRefreshing = true
        }

        Espresso.onView(withId(R.id.swipe_refresh))
            .check(ViewAssertions.matches(Matchers.`is`(isRefreshing())))
    }

    @Test
    fun loadNewsPages() {
        Espresso.onView(withId(R.id.recyclerview)).perform(
            RecyclerViewActions.scrollToPosition<ReportViewHolder>(
                2
            )
        )
        Espresso.onView(withId(R.id.recyclerview)).perform(ViewActions.swipeUp())
        waitForIdle(SLEEP.LARGE)
        assertDisplayed("gAlpha.com")
    }
}