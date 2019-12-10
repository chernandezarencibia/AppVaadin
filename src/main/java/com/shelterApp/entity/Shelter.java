package com.shelterApp.entity;

import java.util.ArrayList;

public class Shelter {

    private int id;
    private String address;
    private String name;
    private String img;


    public Shelter(int id, String address, String name, String img) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    }

