package com.shnsaraswati.applikasiberbagimakanan.model;

public class User {

    private String id;
    private String name;
    private String phone_number;
    private String address;
    private String birth_date;
    private String password;

    public User(String phone_number, String password) {
        this.phone_number = phone_number;
        this.password = password;
    }

    public User(String name, String phone_number, String address, String birth_date, String password) {
        this.name = name;
        this.phone_number = phone_number;
        this.address = address;
        this.birth_date = birth_date;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
