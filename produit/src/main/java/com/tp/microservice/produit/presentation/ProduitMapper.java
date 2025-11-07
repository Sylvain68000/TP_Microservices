package com.tp.microservice.produit.presentation;

import com.tp.microservice.produit.application.Produit;

public class ProduitMapper {

    public Produit mapProduit(ProduitDTO produitDTO) {
        if (produitDTO == null) {
            return null;
        }
        Produit produit = new Produit();
        produit.setNom(produitDTO.getNom());
        produit.setPrix(produitDTO.getPrix());
        produit.setDescription(produitDTO.getDescription());
        return produit;
    }

    public ProduitDTO mapToDTO(Produit produit) {
        if (produit == null) {
            return null;
        }
        ProduitDTO dto = new ProduitDTO();
        dto.setId(produit.getId());
        dto.setNom(produit.getNom());
        dto.setPrix(produit.getPrix());
        dto.setDescription(produit.getDescription());
        return dto;
    }
}
