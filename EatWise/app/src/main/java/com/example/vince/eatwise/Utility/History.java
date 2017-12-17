package com.example.vince.eatwise.Utility;

import com.example.vince.eatwise.Constants.Constant;
import com.example.vince.eatwise.Constants.CuisineType;
import com.example.vince.eatwise.Constants.Distance;
import com.example.vince.eatwise.Constants.Price;
import com.example.vince.eatwise.Constants.Rating;
import com.example.vince.eatwise.QueryData.QueryFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import lombok.Getter;
import lombok.Setter;

import static com.example.vince.eatwise.Constants.Constant.HISTORY_LENGTH;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Getter
@Setter
public class History {
    private Integer queryLength;
    private Integer viewLength;
    private List<Query> pastQuery;
    private List<Viewed> pastViewing;

    public History() {
        this.queryLength = 0;
        this.viewLength = 0;
        this.pastQuery = new ArrayList<>();
        this.pastViewing = new ArrayList<>();
    }

    /**
     * Adding a new query to the history
     * @param newQuery The query to be added
     */
    public void addQuery(final Query newQuery) {
        if (queryLength < HISTORY_LENGTH) {
            pastQuery.add(newQuery);
        } else {
            pastQuery.set(queryLength % HISTORY_LENGTH, newQuery);
        }

        queryLength++;
    }

    /**
     * Adding a restaurant to the viewed list if it is viewed by the user
     * @param newViewed The viewed restaurant tio be added
     */
    public void addViewed(final Viewed newViewed) {
        if (viewLength < HISTORY_LENGTH) {
            pastViewing.add(newViewed);
        } else {
            pastViewing.set(viewLength % HISTORY_LENGTH, newViewed);
        }

        viewLength++;
    }

    ;

    /**
     * Modify the calculated preference by frequency of the keys of user's query
     * Store the preference of the user
     * Still in progress
     * @param currentPreference User class would pass in its own preference to get an updated preference
     */
    //calculate preference
    public void updatePreference(final PreferenceData currentPreference) {
        final Integer qLen = (queryLength > HISTORY_LENGTH) ? HISTORY_LENGTH : queryLength;
        final Integer vLen = (viewLength > HISTORY_LENGTH) ? HISTORY_LENGTH : viewLength;

        Double freType, freCost, freRating, freDistance;
        freType = freCost = freRating = freDistance = 1.0; //for new users, there are no available frequency
        if(queryLength !=0){
            //for query, get key frequency
            Double numType, numCost, numRating, numDistance;
            numType = numCost = numRating = numDistance = 0.0;

            for (final Query query : pastQuery) {
                if (!query.getType().equals("food")) {
                    numType++;
                }
                if (query.getCost() != Price.INEXPENSIVE.toDouble()) {
                    numCost++;
                }
                if (Rating.FOUR.toDouble() != (query.getRating())) {
                    numRating++;
                }
                if (query.getDistance() != 0.0) {
                    numDistance++;
                }
            }
            freType = numType / qLen;
            freCost = numCost / qLen;
            freRating = numRating / qLen;
            freDistance = numDistance / qLen;
        }

        Double cost, rating, distance;
        cost = Constant.INIT_PREF_COST;
        rating = Constant.INIT_PREF_RATING;
        distance = Constant.INIT_PREF_DISTANCE;
        HashMap.Entry<String, Integer> maxEntry = null; //for getting the maximal value of typeRecord

        if(viewLength != 0){
            //for viewed, get most viewed type, cost, rating, distance
            //keep type count
            HashMap<String, Integer> typeRecord = new HashMap<String, Integer>();
            HashSet<String> types =  new HashSet<String>();
            for (CuisineType type : CuisineType.values()) {
                types.add(type.toString());
            }

            //keep cost, rating, distance count
            Integer[] costRecord = new Integer[4];
            Integer[] ratingRecord = new Integer[6];
            Integer[] distanceRecord = new Integer[4];

            for (final Viewed viewed : pastViewing) {
                //count type appearance
                PreferenceData curr_pref = viewed.getRestaurant().getPreferenceFeature();
                String type = curr_pref.getType();
                if (types.contains(type)) {
                    if (typeRecord.get(type) == null) {
                        typeRecord.put(type, 1);
                    } else typeRecord.put(type, typeRecord.get(type) + 1);
                } else System.out.print("Error, unknown type detected by history.");

                //count cost
                cost = curr_pref.getCost();
                if (cost < 0.0) System.out.print("Error, illegal cost preference (negative).");
                else if (cost < Price.INEXPENSIVE.toDouble()) { //inexpensive
                    costRecord[0] += 1;
                } else if (cost < Price.MODERATE.toDouble()) {
                    costRecord[1] += 1;
                } else if (cost < Price.PRICY.toDouble()) {
                    costRecord[2] += 1;
                } else
                    costRecord[3] += 1;

                //count rating
                rating = curr_pref.getRating();
                if (rating < 0.0) System.out.print("Error, illegal value of rating score.");
                int i = 0;
                while (i < 5) {
                    if (rating < i + 1) ratingRecord[i] += 1;
                    i++;
                }

                //count distance
                distance = curr_pref.getDistance();
                if (distance < 0.0) System.out.print("Error, illegal distance value.");
                else if (distance <= Distance.WALKING.toDouble()) distanceRecord[0] += 1;
                else if (distance <= Distance.BIKE.toDouble()) distanceRecord[1] += 1;
                else if (distance <= Distance.CAR.toDouble()) distanceRecord[2] += 1;
                else distanceRecord[3] += 1;
            }

            //get most frequent type, ... as preference
            for (HashMap.Entry<String, Integer> entry : typeRecord.entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }

            int largest = 0;
            for (int i = 1; i < costRecord.length; i++) {
                if (costRecord[i] > costRecord[largest]) largest = i;
            }

            switch (largest) {
                case 0:
                    cost = Price.INEXPENSIVE.toDouble(); //this is also the default value for empty view array
                    break;
                case 1:
                    cost = Price.MODERATE.toDouble();
                    break;
                case 2:
                    cost = Price.HIGHENG.toDouble();
                    break;
                case 3:
                    cost = Price.PRICY.toDouble();
                    break;
            }

            largest = 0;
            for (int i = 1; i < ratingRecord.length; i++) {
                if (ratingRecord[i] > ratingRecord[largest]) largest = i;
            }

            rating = (largest == 0)? 4.0 : (double) largest;

            largest = 0;
            for (int i = 1; i < distanceRecord.length; i++) {
                if (distanceRecord[i] > distanceRecord[largest]) largest = i;
            }

            switch (largest) {
                case 0:
                    distance = Distance.WALKING.toDouble();
                    break;
                case 1:
                    distance = Distance.BIKE.toDouble();
                    break;
                case 2:
                    distance = Distance.CAR.toDouble();
                    break;
                case 3:
                    distance = Distance.LONGTRIP.toDouble();
                    break;
            }
        }


        currentPreference.updatePreference((maxEntry == null)?Constant.INIT_PREF_TYPE:maxEntry.getKey(), cost, rating, distance, freType, freCost, freRating, freDistance);
    }

    /**
     * Assign weight to viewed and visited restaurants from history to calculate recommendation
     * Still in progress
     * @param currentPreference  User class would pass in its own preference to get an updated preference
     * @param  results  the search results based on user preference, used as recommendation
     */
    //get recommended restaurants by preference from historically viewed
    public JsonArray calculateByPreference(final PreferenceData currentPreference, final JsonArray results, final Double longitude, final Double latitude ){
        if(results == null || results.size() <= Constant.NUM_RECOMMEND/2) return results;

        //var to consider
        /* not incorporated
        String type;
        */
        Double cost;
        Double distance;
        Double rating;
        //score to index map
        TreeMap<Double, Integer> res_scores = new TreeMap<Double, Integer>();

        //select num_record for recommend list; being refactored
        for(int i = 0; i < results.size(); i++){
            Double score;

            //get restaurant location
            Double res_latitude = results.get(i).getAsJsonObject().get("coordinates").getAsJsonObject().get("latitude").getAsDouble();
            Double res_longitude = results.get(i).getAsJsonObject().get("coordinates").getAsJsonObject().get("longitude").getAsDouble();
            distance = getLinearDistance(latitude, longitude, res_latitude, res_longitude);
            //get rating
            rating = results.get(i).getAsJsonObject().get("rating").getAsDouble();
            //get cost
            switch(results.get(i).getAsJsonObject().get("price").getAsString()){
                case "$": cost = Price.INEXPENSIVE.toDouble(); break;
                case "$$": cost = Price.MODERATE.toDouble(); break;
                case "$$$": cost = Price.PRICY.toDouble(); break;
                case "$$$$": cost = Price.HIGHENG.toDouble(); break;
                default: cost = 0.0; //indicating price info not available
            }

            score = ((distance <= currentPreference.getDistance()) ? (Constant.DISTANCE_WEIGHT * currentPreference.getFreqByDistance()) : 0.0)
                    +
                    ((rating >= currentPreference.getRating()) ? (Constant.RATING_WEIGHT * currentPreference.getFreqByRating()) : 0.0)
                    +
                    ((cost == 0.0)?0.0:((cost <= currentPreference.getCost())?(Constant.COST_WEIGHT * currentPreference.getFreqByCost()):0.0));
            System.out.printf("score: %f; index: %d \n", distance, i);
            //break tie while adding the entry
            addTomap(res_scores, score, i);
        }
        //assemble a new JsonArray by the recommendation result
        JsonArray rec_result = new JsonArray();
        //reverse the map order to get a descending order by score.
        NavigableMap<Double, Integer> rec_map = res_scores.descendingMap();
        Iterator itr = rec_map.entrySet().iterator();
        int num_res = 0;
        for(NavigableMap.Entry<Double, Integer> e: rec_map.entrySet()){
            if(num_res > Constant.NUM_RECOMMEND) break;
            num_res += 1;
            rec_result.add(results.get(e.getValue()));
        }
        return rec_result;
    }

    public Double getLinearDistance(Double user_lat, Double user_long, Double latitude, Double longitude){
        return Math.sqrt(Math.pow(user_lat - latitude, 2) + Math.pow(user_long - longitude, 2));
    }
    //recursively resolve key conflict by breaking tie of scores arbitrarily
    public void addTomap(TreeMap<Double, Integer> treeMap, Double key_score, Integer value_index){
        if(treeMap.get(key_score) == null) treeMap.put(key_score, value_index);
        else addTomap(treeMap, key_score + 0.01, value_index);
    }
}
