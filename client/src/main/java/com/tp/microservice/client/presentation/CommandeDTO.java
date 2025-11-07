package com.tp.microservice.client.presentation;

import java.util.List;

public class CommandeDTO {
    private int id;
    private String nom;
    private List<ProduitDTO> produits;

    public CommandeDTO(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public CommandeDTO(int id, String nom, List<ProduitDTO> produits) {
        this.id = id;
        this.nom = nom;
        this.produits = produits;
    }

    public CommandeDTO() {
    }

    public int getId() {
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

    public List<ProduitDTO> getProduits() {
        return produits;
    }

    public void setProduits(List<ProduitDTO> produits) {
        this.produits = produits;
    }
}