package com.example.vince.eatwise;

import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
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
public class SearchFragmentInstrumentalTest {
    private SearchFragment search = new SearchFragment();

    private static final String LOCATION = "westwood";
    private final ViewInteraction locationEdit = onView(withId(R.id.editTextLocation));
    private final ViewInteraction distanceEdit = onView(withId(R.id.editTextDistance));
    private final ViewInteraction priceEdit = onView(withId(R.id.editTextPrice));
    private final ViewInteraction keywordEdit = onView(withId(R.id.editTextKeyword));

    @Rule
    public ActivityTestRule<NavigationDrawerActivity> navigationActivity = new ActivityTestRule<>(NavigationDrawerActivity.class);

    @Before
    public void init(){
        navigationActivity.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void assertAllInputBoxDisplayed() {
        locationEdit.check(matches(isDisplayed()));
        distanceEdit.check(matches(isDisplayed()));
        priceEdit.check(matches(isDisplayed()));
        keywordEdit.check(matches(isDisplayed()));

    }

    @Test
    public void assertErrorMessage() {
        locationEdit.perform(typeText(""));
        locationEdit.check(matches(withHint("  Enter search location  ")));
        locationEdit.check(matches(hasErrorText("Please specify the search address.")));
    }

    @Test
    public void assertValidInput() {
        locationEdit.perform(typeText(LOCATION));
        locationEdit.check(matches(hasFocus()));
    }
}