package com.ihariza.news.rule

import androidx.test.espresso.IdlingRegistry
import com.ihariza.news.presentation.view.util.IdlingResource
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class IdlingResourceRule : TestWatcher() {
    private val idlingResource = IdlingResource.countingIdlingResource

    override fun starting(description: Description?) {
        IdlingRegistry.getInstance().register(idlingResource)
        super.starting(description)
    }

    override fun finished(description: Description?) {
        IdlingRegistry.getInstance().unregister(idlingResource)
        super.starting(description)
    }
}