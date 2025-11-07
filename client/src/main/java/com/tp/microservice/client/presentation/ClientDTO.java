package com.tp.microservice.client.presentation;

import java.util.List;

public class ClientDTO {
    private int id;
    private String nom;
    private String prenom;
    private String mail;
    private String telephone;
    private List<CommandeDTO> Commandes;

    public ClientDTO() {}

    public ClientDTO(int id, String nom, String prenom, String mail, String telephone, List<CommandeDTO> Commandes) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.telephone = telephone;
        this.Commandes = Commandes; 
    }
        public ClientDTO(int id, String nom, String prenom, String mail, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.telephone = telephone;
    }

    public Integer getId() {
        return id;
    }
    public void setId(int id) {
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
    public List<CommandeDTO> getCommandes() {
        return Commandes;
    }

    public void setCommandes(List<CommandeDTO> Commandes) {
        this.Commandes = Commandes;
    }

}
