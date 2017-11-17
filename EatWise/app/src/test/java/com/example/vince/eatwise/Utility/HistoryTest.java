package com.example.vince.eatwise.Utility;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Maximus on 2017/11/16.
 */

public class HistoryTest{
    private static final Query test_Query = new Query("Mexican", 0.0, Preference_Data.Rating.FOUR, 100.0, "test_restaurant");
    private static final Viewed test_View = new Viewed(new Restaurant("test_Restaurant", "pos 10, 10", "Mexican",10.0, Preference_Data.Rating.FOUR ));

    private History test_history = new History();

    @Test
    public void addQuerySuccess(){
        test_history.add_query(test_Query);
        assertEquals(test_history.past_query.isEmpty(), false);
        assertEquals(test_history.past_query.get(0).getType(), test_Query.getType());
        assertEquals(test_history.past_query.get(0).getCost(), test_Query.getCost());
        assertEquals(test_history.past_query.get(0).getRating(), test_Query.getRating());
        assertEquals(test_history.past_query.get(0).getDistance(), test_Query.getDistance());
    }

    @Test
    public void addViewedSuccess(){
        test_history.add_viewed(test_View);
        assertEquals(test_history.past_viewing.isEmpty(), false);
        assertEquals(test_history.past_viewing.get(0).getRestaurant().getName(), test_View.getRestaurant().getName());
        assertEquals(test_history.past_viewing.get(0).getRestaurant().getLocation(), test_View.getRestaurant().getLocation());
        assertEquals(test_history.past_viewing.get(0).getRestaurant().getFeature().getType(), test_View.getRestaurant().getFeature().getType());
        assertEquals(test_history.past_viewing.get(0).getRestaurant().getFeature().getCost(), test_View.getRestaurant().getFeature().getCost());
        assertEquals(test_history.past_viewing.get(0).getRestaurant().getFeature().getRating(), test_View.getRestaurant().getFeature().getRating());
    }
}
