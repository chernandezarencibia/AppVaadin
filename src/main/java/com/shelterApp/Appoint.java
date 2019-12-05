package com.shelterApp;

import java.time.LocalDateTime;

public class Appoint {

    private String dogName;
    private String userName;
    private String date;

    public Appoint(String dogName, String userName, String date) {
        this.dogName = dogName;
        this.userName = userName;
        this.date = date;
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
