package com.example.vince.eatwise;

import android.os.Bundle;
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
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityInstrumentedTest {
    private Bundle b = new Bundle();
    private static final String EMAIL = "mike@gmail.com";
    private static final String PASSWORD = "cs130project";

    @Rule
    public ActivityTestRule<LoginActivity> navigationActivity = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void onCreate() throws Exception {
        onView(withId(R.id.email))
                .perform(typeText(EMAIL))
                .check(matches(withText(EMAIL)));

        onView(withId(R.id.password))
                .perform(typeText(PASSWORD))
                .check(matches(withText(PASSWORD)));
    }

    @Test
    public void onClick() throws Exception {

    }

}