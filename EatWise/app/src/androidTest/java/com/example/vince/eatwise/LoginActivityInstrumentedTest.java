package com.example.vince.eatwise;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityInstrumentedTest {
    @Rule
    public ActivityTestRule<LoginActivity> navigationActivity = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void onCreate() throws Exception {
    }

    @Test
    public void onStart() throws Exception {
    }

    @Test
    public void onClick() throws Exception {
    }

}