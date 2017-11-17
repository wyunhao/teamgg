package com.example.vince.eatwise.Utility;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class Preference_DataTest {
    private static final String TYPE = "type";
    private static final Double COST = new Double(0.0);
    private static final Preference_Data.Rating RATING = Preference_Data.Rating.ONE;
    private static final Double DISTANCE = new Double(0.0);

    private Preference_Data pd;

    @Before
    public void setUp() {
        pd = new Preference_Data(TYPE, COST, RATING, DISTANCE);
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
        pd.update_preference("new", testDouble, Preference_Data.Rating.TWO,
                testDouble, testDouble, testDouble, testDouble, testDouble);

        assertEquals("new", pd.getType());
        assertEquals(testDouble, pd.getCost());
        assertEquals(Preference_Data.Rating.TWO, pd.getRating());
        assertEquals(testDouble, pd.getDistance());
        assertEquals(testDouble, pd.getFreqByType());
        assertEquals(testDouble, pd.getFreqByRating());
        assertEquals(testDouble, pd.getFreqByDistance());
        assertEquals(testDouble, pd.getFreqByCost());
    }

}