package com.imt.framework.web.tuto.presentation;

import java.util.List;

public class ClientDTO {
    private Integer id;
    private String nom;
    private String prenom;
    private String mail;
    private String telephone;
    private List<Integer> produitsCommandesIds;
    
   
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
