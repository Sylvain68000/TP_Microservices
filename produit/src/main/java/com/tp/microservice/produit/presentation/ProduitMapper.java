package com.imt.framework.web.tuto.presentation;

import com.imt.framework.web.tuto.application.Produit;

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
