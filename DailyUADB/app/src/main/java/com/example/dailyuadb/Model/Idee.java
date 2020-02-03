package com.example.dailyuadb.Model;

public class Idee {

    private String email;
    private String date;
    private String heure;
    private String id;
    private String description;


    public Idee(String id,String email, String description, String date, String heure) {
        this.id = id;
        this.email = email;
        this.description = description;
        this.date = date;
        this.heure = heure;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
