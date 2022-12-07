package com.smchoi.matzip.vos;

import com.smchoi.matzip.entities.data.PlaceEntity;

public class PlaceVo extends PlaceEntity {
    private Boolean isClose;
    private Boolean isBreakNull;
    private Boolean isBreak;
    private String placeText;
    private double score;

    private int reviewCount;

    public int getReviewCount() {
        return reviewCount;
    }

    public PlaceVo setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
        return this;
    }

    public double getScore() {
        return score;
    }

    public PlaceVo setScore(double score) {
        this.score = score;
        return this;
    }

    public Boolean getBreakNull() {
        return isBreakNull;
    }

    public PlaceVo setBreakNull(Boolean breakNull) {
        isBreakNull = breakNull;
        return this;
    }

    public String getPlaceText() {
        return placeText;
    }

    public PlaceVo setPlaceText(String placeText) {
        this.placeText = placeText;
        return this;
    }

    public Boolean getBreak() {
        return isBreak;
    }

    public PlaceVo setBreak(Boolean aBreak) {
        isBreak = aBreak;
        return this;
    }

    public Boolean getClose() {
        return isClose;
    }

    public PlaceVo setClose(Boolean close) {
        isClose = close;
        return this;
    }
}
