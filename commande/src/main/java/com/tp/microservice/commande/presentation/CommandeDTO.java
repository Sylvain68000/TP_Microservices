package com.tp.microservice.commande.presentation;

import java.util.List;

import com.tp.microservice.commande.application.ProduitDAO;
import com.tp.microservice.commande.application.StatusCommande;

public class CommandeDTO {
    private Integer id;
    private String nom;
    private StatusCommande statut;
    private List<ProduitDTO> produits;
    private Integer clientId;

    public CommandeDTO() {
    }

    public CommandeDTO(Integer id, String nom, StatusCommande statut, List<ProduitDTO> produits, Integer clientId ) {
        this.id = id;
        this.nom = nom;
        this.statut = statut;
        this.produits = produits;
        this.clientId = clientId;
    }

    public CommandeDTO(Integer id, String nom, StatusCommande statut, Integer clientId) {
        this.id = id;
        this.nom = nom;
        this.statut = statut;
        this.clientId = clientId;
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

    public List<ProduitDTO> getProduits() {
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

    public void setProduits(List<ProduitDTO> produits) {
        this.produits = produits;
    }
    public Integer getClientId() {
        return clientId;
    }
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}
