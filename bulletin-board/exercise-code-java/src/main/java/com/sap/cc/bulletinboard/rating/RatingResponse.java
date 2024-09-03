package com.sap.cc.bulletinboard.rating;

public class RatingResponse {

    private float average_rating;
    public RatingResponse() {
    }

    public RatingResponse(float average_rating) {
        this.average_rating = average_rating;
    }
    public float getAverage_rating() {
        return average_rating;
    }
}