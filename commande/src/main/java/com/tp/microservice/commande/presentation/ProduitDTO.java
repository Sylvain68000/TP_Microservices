package com.tp.microservice.commande.presentation;

public class ProduitDTO {
    private int id;
    private String nom;
    private String description;
    private Double prix;


    public ProduitDTO() {
    }

    public ProduitDTO(int id, String nom, String description, Double prix) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrix() {
        return prix;
    }
    public void setId(int id) {
        this.id = id;
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