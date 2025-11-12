package com.tp.microservice.commande.presentation;

import java.util.List;

public class CreationCommandeDTO {
    private String nom;
    private List<Integer> produits;
    private Integer clientId;

    public CreationCommandeDTO() {
    }

    public CreationCommandeDTO(String nom, List<Integer> produits, Integer clientId) {
        this.nom = nom;
        this.produits = produits;
        this.clientId = clientId;
    }  


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public List<Integer> getProduits() {
        return produits;
    }

    public void setProduits(List<Integer> produits) {
        this.produits = produits;
    }
     public Integer getClientId() {
        return clientId;
    }
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}
