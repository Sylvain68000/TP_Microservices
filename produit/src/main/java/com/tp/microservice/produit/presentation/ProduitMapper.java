package com.tp.microservice.produit.presentation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tp.microservice.produit.application.Produit;

@Component
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

    public Produit mapCreationDTOToProduit(CreationProduitDTO produitdto) {
        Produit produit = new Produit();
        produit.setNom(produitdto.getNom());
        produit.setDescription(produitdto.getDescription());
        produit.setPrix(produitdto.getPrix());
        return produit;
    }

    public void appliquerPatchProduit (Produit produit, UpdateProduitDTO Produitdto) {
        if (Produitdto.getNom() != null) {
            produit.setNom(Produitdto.getNom());
        }
        if (Produitdto.getDescription() != null) {
            produit.setDescription(Produitdto.getDescription());
        }
        if (Produitdto.getPrix() != null) {
            produit.setPrix(Produitdto.getPrix());
        }
    }

    public List<ProduitDTO> produitsToProduitDTOs(List<Produit> produitsEntites) {
            return produitsEntites.stream()
            .map(this::mapProduitToProduitDTO) // "Pour chaque produit dans la liste, appelle mapProduitToProduitDTO"
            .collect(Collectors.toList());
}
}