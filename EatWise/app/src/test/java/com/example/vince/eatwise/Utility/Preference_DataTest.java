package com.example.vince.eatwise.Utility;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class Preference_DataTest {
    private static final String TYPE = "type";
    private static final Double COST = new Double(0.0);
    private static final Rating RATING = Rating.ONE;
    private static final Double DISTANCE = new Double(0.0);

    private PreferenceData pd;

    @Before
    public void setUp() {
        pd = new PreferenceData(TYPE, COST, RATING, DISTANCE);
    }

    @Test
    public void validateConstructor() {
        assertEquals(TYPE, pd.getType());
        assertEquals(COST, pd.getCost());
        assertEquals(RATING, pd.getRating());
        assertEquals(DISTANCE, pd.getDistance());
        assertEquals(new Double(0.0), pd.getFreqByCost());
        assertEquals(new Double(0.0), pd.getFreqByDistance());
        assertEquals(new Double(0.0), pd.getFreqByRating());
        assertEquals(new Double(0.0), pd.getFreqByType());
    }

    @Test
    public void updatePreferenceSucceed() {
        final Double testDouble = new Double(1.1);
        pd.updatePreference("new", testDouble, Rating.TWO,
                testDouble, testDouble, testDouble, testDouble, testDouble);

        assertEquals("new", pd.getType());
        assertEquals(testDouble, pd.getCost());
        assertEquals(Rating.TWO, pd.getRating());
        assertEquals(testDouble, pd.getDistance());
        assertEquals(testDouble, pd.getFreqByType());
        assertEquals(testDouble, pd.getFreqByRating());
        assertEquals(testDouble, pd.getFreqByDistance());
        assertEquals(testDouble, pd.getFreqByCost());
    }

}