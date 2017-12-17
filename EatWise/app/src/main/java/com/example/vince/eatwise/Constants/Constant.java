package com.example.vince.eatwise.Constants;

import java.util.function.DoubleUnaryOperator;
import java.util.zip.DeflaterOutputStream;

public final class Constant {
    public static final Integer HISTORY_LENGTH = 100;
    public static final Integer NUM_RECOMMEND = 10;

    public static final Double VIEW_WEIGHT = 1.0;
    public static final Double VISIT_WEIGHT = 20.0;

    public static final Double INIT_PREF_COST = 15.0;
    public static final Double INIT_PREF_RATING = 4.0;
    public static final Double INIT_PREF_DISTANCE = 1000.0;
    public static final String INIT_PREF_TYPE = "food";

    public static final Double DISTANCE_WEIGHT = 4.0;
    public static final Double RATING_WEIGHT = 3.0;
    public static final Double COST_WEIGHT = 4.0;
}
