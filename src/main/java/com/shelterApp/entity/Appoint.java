package com.shelterApp.entity;

import java.time.LocalDateTime;

public class Appoint {

    private String dogName;
    private String userName;
    private String date;
    private int dogId;
    private int userId;

    public Appoint(String dogName, int dogId, String userName, int userId, String date) {
        this.dogName = dogName;
        this.userName = userName;
        this.date = date;
        this.dogId = dogId;
        this.userId = userId;
    }
    public Appoint(){};

    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
