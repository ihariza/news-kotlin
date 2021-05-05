package com.nhariza.news.view.base

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.filters.LargeTest
import com.google.gson.Gson
import com.nhariza.news.builder.NewsDtoBuilder
import com.nhariza.news.rule.OkHttpIdlingResourceRule
import io.mockk.MockKAnnotations
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule

@LargeTest
abstract class BaseAndroidTest {

    private val mockServer = MockWebServer()

    @get:Rule
    val okHttpIdlingResourceRule = OkHttpIdlingResourceRule()

    @Before
    fun init() {
        MockKAnnotations.init(this)
        with(mockServer) {
            start(7878)
            dispatcher = object : okhttp3.mockwebserver.Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return MockResponse().apply {
                        setResponseCode(200)
                        setBody(Gson().toJson(NewsDtoBuilder.build()))
                    }
                }
            }
        }
        setup()
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    abstract fun setup()

    protected fun isRefreshing(): Matcher<View>? {
        return object : BoundedMatcher<View, SwipeRefreshLayout>(SwipeRefreshLayout::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("is refreshing")
            }

            override fun matchesSafely(item: SwipeRefreshLayout): Boolean {
                return item.isRefreshing
            }
        }
    }
}