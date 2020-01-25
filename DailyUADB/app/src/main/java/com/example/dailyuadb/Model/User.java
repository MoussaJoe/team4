package com.example.dailyuadb.Model;

public class User {
    public String nom;
    public String prenom;
    public String numCarte;
    public String email;
    public String password;

    public User() {
    }

    public User(String nom, String prenom, String numCarte, String email, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.numCarte = numCarte;
        this.email = email;
        this.password = password;
    }


}
