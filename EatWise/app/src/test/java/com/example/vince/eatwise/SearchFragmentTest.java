package com.example.vince.eatwise;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.ViewInteraction;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import android.support.test.rule.*;
import android.support.test.runner.AndroidJUnit4;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class SearchFragmentTest {
    private static final String LOCATION = "westwood";

    @Test
    public void onCreateViewValidation() {
        ViewInteraction locationEdit = onView(withId(R.id.editTextLocation));
        locationEdit.perform(typeText(LOCATION));

    }
}