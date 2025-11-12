package com.tp.microservice.client.application;

import java.util.List;

import com.tp.microservice.client.presentation.ProduitDTO;

public class CommandeDAO {
    private int id;
    private String nom;
    private List<ProduitDTO> produits;
    private String statut;
    private Integer clientId;


    public CommandeDAO() {
    }

    public CommandeDAO(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public CommandeDAO(int id, String nom, List<ProduitDTO> produits) {
        this.id = id;
        this.nom = nom;
        this.produits = produits;
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
        public String getStatut() {
        return statut;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Integer getClientId() {
        return clientId;
    }
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}