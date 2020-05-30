package com.example.dabbaapp.Model;

public class ModelDabbawala {
    String location,phoneNO,special,uid,Name;

    public ModelDabbawala(String location, String phoneNO, String special, String uid, String name) {
        this.location = location;
        this.phoneNO = phoneNO;
        this.special = special;
        this.uid = uid;
        Name = name;
    }

    public String getPhoneNO() {
        return phoneNO;
    }

    public void setPhoneNO(String phoneNO) {
        this.phoneNO = phoneNO;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ModelDabbawala() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
