package com.project.dicodingplayground.practice_modul.tdd.ui.list

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import org.junit.Test
import com.project.dicodingplayground.R
import com.project.dicodingplayground.practice_modul.tdd.data.remote.retrofit.ApiConfig
import com.project.dicodingplayground.practice_modul.tdd.utils.EspressoIdlingResource
import com.project.dicodingplayground.practice_modul.tdd.utils.JsonConverter
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class NewsFragmentTest {

    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        mockWebServer.start(8080)
        ApiConfig.BASE_URL = "http://127.0.0.1:8080/"
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun getHeadlineNews_Success() {
        val bundle = Bundle()
        bundle.putString(NewsFragment.ARG_TAB, NewsFragment.TAB_NEWS)
        launchFragmentInContainer<NewsFragment>(bundle, R.style.Theme_DicodingPlayground)

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("success_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.rv_news2))
            .check(matches(isDisplayed()))

        onView(withText("Mysterious Twist Revealed in Saga of Human-Neanderthal Hybrid Child - ScienceAlert"))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_news2))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Spiral starburst galaxy glows in gorgeous Hubble Telescope image - Space.com"))
                )
            )
    }

    @Test
    fun getHeadlineNews_Error() {
        val bundle = Bundle()
        bundle.putString(NewsFragment.ARG_TAB, NewsFragment.TAB_NEWS)
        launchFragmentInContainer<NewsFragment>(bundle, R.style.Theme_DicodingPlayground)

        val mockResponse = MockResponse()
            .setResponseCode(500)
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.tv_error))
            .check(matches(isDisplayed()))
        onView(withText("Something Wrong"))
            .check(matches(isDisplayed()))
    }
}