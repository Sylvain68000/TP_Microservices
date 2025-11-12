package com.tp.microservice.client.presentation;

import java.util.List;

public class UpdateClientDTO {
    private String nom;
    private String prenom;
    private String mail;
    private String telephone;
    private List<Integer> idCommandes;

    
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public List<Integer> getIdCommandes() {
        return idCommandes;
    }
    public void setIdCommandes(List<Integer> idCommandes) {
        this.idCommandes = idCommandes;
    }


    
}
