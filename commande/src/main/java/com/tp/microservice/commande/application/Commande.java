package com.tp.microservice.commande.application;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nom;
    @Enumerated(EnumType.STRING)
    private StatusCommande statut;

    @ElementCollection
    @CollectionTable(name = "commande_id_produits", joinColumns = @JoinColumn(name = "commande_id"))
    @Column(name = "produit_id")
    private List<Integer> idProduits;

    
    public Commande() {
    }

    public Commande(String nom, StatusCommande statut, List<Integer> idProduits) {
        this.nom = nom;
        this.statut = statut;
        this.idProduits = idProduits;
    }


    // Getters and Setters

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

    public StatusCommande getStatut() {
        return statut;
    }

    public void setStatut(StatusCommande statut) {
        this.statut = statut;
    }
    public List<Integer> getIdProduits() {
        return idProduits;
    }
    public void setIdProduits(List<Integer> idProduits) {
        this.idProduits = idProduits;
    }
}
