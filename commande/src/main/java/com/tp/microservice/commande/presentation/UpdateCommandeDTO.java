package com.tp.microservice.commande.presentation;

import java.util.List;

import com.tp.microservice.commande.application.StatusCommande;

    public class UpdateCommandeDTO {
    private String nom;
    private StatusCommande statut;
    private List<Integer> produits;
    private Integer clientId;
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public StatusCommande getStatut() {
        return statut;
    }
    public void setStatut(StatusCommande statut) {
        this.statut = statut;
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
