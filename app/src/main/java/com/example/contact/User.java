package com.example.contact;

public class User {
    String name, email, phone;
    int active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {return phone;}

    public void setPhone(String phone) {this.phone = phone;}

    public Integer getActive() {return active;}

    public void setActive(Integer active) {this.active = active;}
}
