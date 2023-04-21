package com.example.amigos.metromate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

public class BookingsObject {

    private String source;
    private String destination;
    private int fare;
    private Serializable time;
    private Serializable date;

    private String id;
    FirebaseUser user;

    public BookingsObject() {
    }

    public BookingsObject(String source, String destination, int fare, Serializable time, Serializable date) {
        this.source = source;
        this.destination = destination;
        this.fare = fare;
        this.time = time;
        this.date = date;
        user = FirebaseAuth.getInstance().getCurrentUser();
        id = user.getUid();
    }

    // Getters and setters for the fields

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public Serializable getTime() {
        return time;
    }

    public void setTime(Serializable time) {
        this.time = time;
    }

    public Serializable getDate() {
        return date;
    }

    public void setDate(Serializable date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
