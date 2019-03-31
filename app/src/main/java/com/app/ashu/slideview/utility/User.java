package com.app.ashu.slideview.utility;

public class User {

    private int id;
    private String unique_id;
    private String phoneNumber;
    private String name;

    public User()
    {
        id=0;
        unique_id="NA";
        phoneNumber="NA";
        name="NA";
    }

    public User(int id, String unique_id, String phoneNumber, String name) {
        this.id = id;
        this.unique_id = unique_id;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
