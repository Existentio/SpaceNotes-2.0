package com.existentio.spacenotesmvc.model;

/**
 * Created by Георгий on 19.09.2017.
 */

public class Notes {

    private String description;
    private String date;
    private int id;

    public Notes(String description, String date, int id) {
        this.description = description;
        this.date = date;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }


}
