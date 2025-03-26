package com.project.dicodingplayground.practice_modul.tdd.ui.list

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.project.dicodingplayground.practice_modul.tdd.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import com.project.dicodingplayground.R
import com.project.dicodingplayground.practice_modul.tdd.ui.detail.NewsDetailActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeActivityTest {


    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun loadHeadlineNews_Success() {
        onView(withId(R.id.rv_news2)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_news2)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
    }

    @Test
    fun loadHeadlineDetail_Success() {
        Intents.init()
        onView(withId(R.id.rv_news2)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        intended(hasComponent(NewsDetailActivity::class.java.name))
        onView(withId(R.id.webView)).check(matches(isDisplayed()))
    }

    @Test
    fun loadBookmarkedNews_Success() {
        onView(withId(R.id.rv_news2)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.action_bookmark)).perform(click())
        pressBack()
        onView(withText("Bookmark")).perform(click())
        onView(withId(R.id.rv_news2)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_news2)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.webView)).check(matches(isDisplayed()))
        onView(withId(R.id.action_bookmark)).perform(click())
        pressBack()
    }
}