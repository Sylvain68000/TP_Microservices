package com.tp.microservice.produit.presentation;

import com.tp.microservice.produit.application.Produit;

public class ProduitMapper {

    public Produit mapProduitDTOtoProduit(ProduitDTO produitDTO) {

        Produit produit = new Produit();
        produit.setNom(produitDTO.getNom());
        produit.setPrix(produitDTO.getPrix());
        produit.setDescription(produitDTO.getDescription());
        return produit;
    }

    public ProduitDTO mapProduitToProduitDTO(Produit produit) {
        ProduitDTO Produitdto = new ProduitDTO();
        Produitdto.setId(produit.getId());
        Produitdto.setNom(produit.getNom());
        Produitdto.setPrix(produit.getPrix());
        Produitdto.setDescription(produit.getDescription());
        return Produitdto;
    }
}
