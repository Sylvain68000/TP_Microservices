package com.tp.microservice.client.presentation;

import java.util.List;

public class CreationClientDTO {

    private Integer id;
    private String nom;
    private String prenom;
    private String mail;
    private String telephone;
    private List<Integer> idCommandes;

    public CreationClientDTO() {}

    public CreationClientDTO(Integer id, String nom, String prenom, String mail, String telephone, List<Integer> idCommandes) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.telephone = telephone;
        this.idCommandes = idCommandes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
