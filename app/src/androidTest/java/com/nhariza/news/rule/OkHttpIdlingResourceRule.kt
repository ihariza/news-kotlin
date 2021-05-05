package com.nhariza.news.rule

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.OkHttpClient
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.java.KoinJavaComponent.inject

class OkHttpIdlingResourceRule: TestRule {

    private val okHttpClient: OkHttpClient by inject(OkHttpClient::class.java)
    private val resource : IdlingResource = OkHttp3IdlingResource.create("okHttpClient", okHttpClient)

    override fun apply(base: Statement, description: Description): Statement {
        return object: Statement() {
            override fun evaluate() {
                IdlingRegistry.getInstance().register(resource)
                base.evaluate()
                IdlingRegistry.getInstance().unregister(resource)
            }
        }
    }
}