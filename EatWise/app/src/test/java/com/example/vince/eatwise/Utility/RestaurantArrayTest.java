package com.example.vince.eatwise.Utility;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;
import java.util.ArrayList;

public class RestaurantArrayTest {
    private static final String NAME = "name";
    private static final String LOCATION = "location";
    private static final String TYPE = "type";
    private static final Double COST = new Double("0.0");
    private static final Preference_Data.Rating RATING = Preference_Data.Rating.ONE;
    private static final Double DISTANCE = new Double("0.0");

    private RestaurantArray ra;

    @Before
    public void setUp() {
        ra = new RestaurantArray();
    }

    @Test
    public void validateConstructor() {
        assertEquals(new Integer(0), ra.getSize());
        assertEquals(new ArrayList<Restaurant>(), ra.getData_member());
    }

    @Test
    public void add_restaurant() throws Exception {
        ra.add_restaurant(NAME, LOCATION, TYPE, COST, RATING, DISTANCE);
        assertEquals(new Integer(1), ra.getSize());
    }

    @Test
    public void reset() throws Exception {
        ra.reset();
        assertEquals(new Integer(0), ra.getSize());
    }

}