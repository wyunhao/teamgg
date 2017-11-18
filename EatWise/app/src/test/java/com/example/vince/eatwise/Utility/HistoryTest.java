package com.example.vince.eatwise.Utility;

import com.example.vince.eatwise.Constants.Rating;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HistoryTest{
    private static final Query test_Query = new Query("Mexican", 0.0, Rating.FOUR, 100.0, "test_restaurant");
    private static final Viewed test_View = new Viewed(new Restaurant("test_Restaurant", "pos 10, 10", "Mexican",10.0, Rating.FOUR ));

    private History test_history = new History();

    @Test
    public void addQuerySuccess(){
        test_history.addQuery(test_Query);
        assertEquals(test_history.getPastQuery().isEmpty(), false);
        assertEquals(test_history.getPastQuery().get(0).getType(), test_Query.getType());
        assertEquals(test_history.getPastQuery().get(0).getCost(), test_Query.getCost());
        assertEquals(test_history.getPastQuery().get(0).getRating(), test_Query.getRating());
        assertEquals(test_history.getPastQuery().get(0).getDistance(), test_Query.getDistance());
    }

    @Test
    public void addViewedSuccess(){
        test_history.addViewed(test_View);
        assertEquals(test_history.getPastViewing().isEmpty(), false);
        assertEquals(test_history.getPastViewing().get(0).getRestaurant().getName(), test_View.getRestaurant().getName());
        assertEquals(test_history.getPastViewing().get(0).getRestaurant().getLocation(), test_View.getRestaurant().getLocation());
        assertEquals(test_history.getPastViewing().get(0).getRestaurant().getPreferenceFeature().getType(), test_View.getRestaurant().getPreferenceFeature().getType());
        assertEquals(test_history.getPastViewing().get(0).getRestaurant().getPreferenceFeature().getCost(), test_View.getRestaurant().getPreferenceFeature().getCost());
        assertEquals(test_history.getPastViewing().get(0).getRestaurant().getPreferenceFeature().getRating(), test_View.getRestaurant().getPreferenceFeature().getRating());
    }
}
