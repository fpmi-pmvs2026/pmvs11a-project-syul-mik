package com.example.travelnotes.model;

import java.io.Serializable;

public class Note implements Serializable {
    private int id;
    private String placeName;
    private String city;
    private String description;
    private String visitDate;
    private int rating;

    public Note(int id, String placeName, String city, String description, String visitDate, int rating) {
        this.id = id;
        this.placeName = placeName;
        this.city = city;
        this.description = description;
        this.visitDate = visitDate;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public int getRating() {
        return rating;
    }
}