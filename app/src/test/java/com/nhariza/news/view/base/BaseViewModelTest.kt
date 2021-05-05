package com.nhariza.news.view.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class BaseViewModelTest : BaseViewModel() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        MockKAnnotations.init(this)
        setup()
    }

    @After
    fun down() {
        unmockkAll()
        tearDown()
    }

    abstract fun setup()

    open fun tearDown() {}
}