package com.shelterApp.entity;

import java.util.ArrayList;

public class Employee {
    private int id;
    private String name;
    private String lastName1;
    private String lastName2;
    private int telephone;
    private String email;
    private String DNI;


    public Employee(int id, String name, String lastName1, String lastName2, int telephone, String email, String DNI) {
        this.id = id;
        this.name = name;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.telephone = telephone;
        this.email = email;
        this.DNI = DNI;
    }


    public Employee() {
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

    public String getLastName1() {
        return lastName1;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    @Override
    public String toString(){
        return DNI + "," + email + "," + name + "," + lastName1 + "," + lastName2 + "," + telephone;
    }

}
