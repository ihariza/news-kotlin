package com.ihariza.news.presentation.view.news

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import com.ihariza.news.R
import com.ihariza.news.presentation.view.news.adapter.ReportViewHolder
import com.ihariza.news.rule.IdlingResourceRule
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@LargeTest
class NewsFragmentTest {


    @get:Rule
    val idlingRegistry = IdlingResourceRule()

    private lateinit var scenario: FragmentScenario<NewsFragment>

    @Before
    fun setup()  {
        MockKAnnotations.init(this)
        scenario = launchFragmentInContainer<NewsFragment>(themeResId = R.style.AppTheme)
        scenario.recreate()
    }

    @Test
    fun onStartShouldShowRecyclerViewOnFragment() {
        onView(withId(R.id.recyclerview)).check(matches(isDisplayed()))
    }

   @Test
   fun navigationToReportDetail() {
       val mockNavController =  mockk<NavController>(relaxed = true)
            scenario.onFragment {
           Navigation.setViewNavController(it.requireView(), mockNavController)
       }

       onView(withId(R.id.recyclerview)).perform(
               RecyclerViewActions.actionOnItemAtPosition<ReportViewHolder>(0, click())
       )

       onView(
               Matchers.allOf(
                       withId(R.id.action_share),
                       withId(R.id.action_share),
                       ViewMatchers.isDescendantOfA(withId(R.id.toolbar))
               )
       )

//       verify { mockNavController.navigate(
//               NewsFragmentDirections.actionNewsFragmentToReportFragment("3632488c-f36b-463c-952b-04126504f3c2")) }
   }

}