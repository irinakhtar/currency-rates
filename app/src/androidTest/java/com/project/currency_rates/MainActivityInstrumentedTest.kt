package com.project.currency_rates

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {
    private lateinit var stringToBetyped: String

    @get:Rule
    var activityTestRule = ActivityTestRule(MainActivity::class.java)
    @Before
    fun setUp() {
    }

    @Test
    fun mainactivity_test_with_valid_input() {
        stringToBetyped = "1"
        onView(withId(R.id.etBaseCurrency))
            .perform(typeText(stringToBetyped))

        onView(withId(R.id.etBaseCurrency))
            .check(matches(withText(stringToBetyped)))

        val recyclerView = activityTestRule.activity
            .findViewById<RecyclerView>(R.id.rvCurrencyList)
        val itemCount = recyclerView.adapter!!.itemCount
        assertThat(itemCount, `is`(8))
    }

    @Test
    fun mainactivity_test_with_invalid_input() {
        stringToBetyped = "10000000123"
        val expectedInputText = "1000000012"
        onView(withId(R.id.etBaseCurrency))
            .perform(typeText(stringToBetyped))

        onView(withId(R.id.etBaseCurrency))
            .check(matches(withText(expectedInputText)))

        val recyclerView = activityTestRule.activity
            .findViewById<RecyclerView>(R.id.rvCurrencyList)
        val itemCount = recyclerView.adapter!!.itemCount
        assertThat(itemCount, `is`(8))
    }
}