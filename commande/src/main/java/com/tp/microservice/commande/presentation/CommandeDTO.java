package com.tp.microservice.commande.presentation;

import java.util.List;

import com.tp.microservice.commande.application.ProduitDAO;
import com.tp.microservice.commande.application.StatusCommande;

public class CommandeDTO {
    private Integer id;
    private String nom;
    private StatusCommande statut;
    private List<ProduitDAO> produits;

    public CommandeDTO() {
    }

    public CommandeDTO(Integer id, String nom, StatusCommande statut, List<ProduitDAO> produits) {
        this.id = id;
        this.nom = nom;
        this.statut = statut;
        this.produits = produits;
    }
    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public StatusCommande getStatut() {
        return statut;
    }

    public List<ProduitDAO> getProduits() {
        return produits;
    }  

    public void setId(Integer id) {
        this.id = id;
    }   

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setStatut(StatusCommande statut) {
        this.statut = statut;
    }

    public void setProduits(List<ProduitDAO> produits) {
        this.produits = produits;
    }

}
