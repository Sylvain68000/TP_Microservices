package com.imt.framework.web.tuto.application;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nom;
    private String prenom;
    private String mail;
    private String telephone;

    @ElementCollection(fetch = jakarta.persistence.FetchType.EAGER) // listing des produits commandés
    private List<Integer> produitsCommandesIds = new ArrayList<>();
    
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
    public List<Integer> getProduitsCommandesIds() {
        return produitsCommandesIds;
    }
    public void setProduitsCommandesIds(List<Integer> produitsCommandesIds) {
        this.produitsCommandesIds = produitsCommandesIds;
    }


}
