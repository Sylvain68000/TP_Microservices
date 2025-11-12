package com.tp.microservice.produit.presentation;

public class CreationProduitDTO {

    private String nom;
    private String description;
    private Double prix;

    public String getNom() {
        return nom;
    }
    public String getDescription() {
        return description;
    }
    public Double getPrix() {
        return prix;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrix(Double prix) {
        this.prix = prix;
    }
    
}