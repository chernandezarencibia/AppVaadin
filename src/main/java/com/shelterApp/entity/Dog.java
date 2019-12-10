package com.shelterApp.entity;

public class Dog {

private int id;
private String name;
private String breed;
private int age;
private int code;
private String img;


    public Dog(int id, String name, String breed, int age, int code, String img) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.code = code;
        this.img = img;
    }

    public Dog() {
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString(){
        return name + "," + breed + "," + age + "," + code;
    }
}

