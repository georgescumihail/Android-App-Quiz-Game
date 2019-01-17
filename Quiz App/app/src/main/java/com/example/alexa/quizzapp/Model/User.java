package com.example.alexa.quizzapp.Model;

public class User {

    private long id;
    private String userName;
    private String password;
    private String email;
    private String occupation;

    public User() { }

    public User(String userName, String password, String email, String occupation) {
        this.userName = userName;
        this.password = password;
        this.email = email;

        this.occupation = occupation;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}




