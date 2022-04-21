package id.jyotisa.storyapp.ui

import android.os.Bundle
import android.support.test.espresso.contrib.RecyclerViewActions
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import id.jyotisa.storyapp.JsonConverter
import id.jyotisa.storyapp.R
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import id.jyotisa.storyapp.helper.Network.URL
import id.jyotisa.storyapp.helper.Utils.EspressoIdlingResource
import okhttp3.mockwebserver.MockResponse

@RunWith(AndroidJUnit4::class)
@MediumTest
class MainActivityTest{

private val mockWebServer = MockWebServer()
    @Before
    fun setUp() {
        mockWebServer.start(8080)
        URL = "http://127.0.0.1:8080/"
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun getStories_Success() {
        launch(MainActivity::class.java)

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("success_response.json"))
        mockWebServer.enqueue(mockResponse)

        Espresso.onView(withId(R.id.rv_stories))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("Dimas"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}