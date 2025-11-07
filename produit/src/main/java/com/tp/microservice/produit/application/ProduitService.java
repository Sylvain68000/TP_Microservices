package com.tp.microservice.produit.application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tp.microservice.produit.infrastructure.ProduitRepository;




@Service
public class ProduitService {
    @Autowired
    private ProduitRepository produitRepository;

    public List<Produit> listingProduits(){
        return produitRepository.findAll();
    }

    public Produit getProduit(int id){
        Optional<Produit> produit = produitRepository.findById(id);
        if (!produit.isPresent()) {
            throw new jakarta.persistence.EntityNotFoundException("Produit non trouvé avec l'ID : " + id);
        }
        return produit.get();
    }

    public void deleteProduit(int id){
        produitRepository.deleteById(id);
    }

    public Produit createProduit(Produit produit) {
        if (produit == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être null");
        }
        return produitRepository.save(produit);
    }

    public Produit updateProduit(Produit produit) {
        if (produit == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être null");
        }
        if (produit.getId() == null) {
            throw new IllegalArgumentException("L'ID du produit ne peut pas être null pour une mise à jour");
        }
        if (!produitRepository.existsById(produit.getId())) {
            throw new jakarta.persistence.EntityNotFoundException("Produit non trouvé avec l'ID : " + produit.getId());
        }
        return produitRepository.save(produit);
    }
}
