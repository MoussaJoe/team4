package com.example.dailyuadb.Model;

public class User {

    String nom;
    String prenom;
    String email;
    String numCarte;
    String password;

    public User(String nom, String prenom, String email,String numCarte,String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numCarte = numCarte;
        this.password = password;
    }
    
}
