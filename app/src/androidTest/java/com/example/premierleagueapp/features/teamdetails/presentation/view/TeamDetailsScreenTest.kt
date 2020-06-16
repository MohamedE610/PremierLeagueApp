package com.example.premierleagueapp.features.teamdetails.presentation.view

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.premierleagueapp.R
import com.example.premierleagueapp.features.teamdetails.presentation.view.activity.TeamDetailsActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class TeamDetailsScreenTest {

    @get:Rule
    var teamDetailsActivityRule: ActivityTestRule<TeamDetailsActivity> =
        ActivityTestRule(TeamDetailsActivity::class.java, true, false)

    @Test
    fun checkTeamDetailsDisplayedSuccessfully() {
        val intent = Intent()
        // Chelsea FC id -> 61
        intent.putExtra("teamId", 61)
        teamDetailsActivityRule.launchActivity(intent)

        onView(withId(R.id.imgTeamDetails)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTeamDetailsClubColors)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTeamDetailsVenue)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTeamDetailsActiveCompetitions)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTeamDetailsSquad)).check(matches(isDisplayed()))
        onView(withId(R.id.rvTeamDetailsActiveCompetitions)).check(matches(isDisplayed()))
        onView(withId(R.id.rvTeamDetailsSquad)).check(matches(isDisplayed()))

    }

    @Test
    fun checkTeamDetailsBtnFavouriteClickedSuccessfully() {
        val intent = Intent()
        // Chelsea FC id -> 61
        intent.putExtra("teamId", 61)
        teamDetailsActivityRule.launchActivity(intent)

        onView(withId(R.id.fabTeamDetailsFavourite)).check(matches(isDisplayed())).perform(click())

    }

}