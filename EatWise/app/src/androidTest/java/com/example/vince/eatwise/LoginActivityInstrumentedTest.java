package com.example.vince.eatwise;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityInstrumentedTest {
    @Rule
    public ActivityTestRule<LoginActivity> navigationActivity = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void onCreate() throws Exception {
        onView(withId(R.layout.activity_login))
                .perform(onCreate();)
                .check(matches(isDisplayed()));
    }

    @Test
    public void onStart() throws Exception {
    }

    @Test
    public void onClick() throws Exception {
    }

}