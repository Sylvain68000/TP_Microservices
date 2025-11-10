package com.tp.microservice.produit.application;

import java.util.List;
import java.util.NoSuchElementException;

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
     return produitRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produit non trouvé avec l'ID: " + id));
    }

    public List<Produit> getProduitsByIds(List<Integer> ids) {
        return produitRepository.findByIdIn(ids);
    }

    public Produit updateProduit(Produit produit) {
        return produitRepository.save(produit);
    }
    public void deleteProduit(int id){
       Produit p = this.getProduit(id); // (Lèvera une erreur 404 si non trouvé)
        produitRepository.delete(p);
    }
    public Produit creationProduit(Produit produit)
    {
        return produitRepository.save(produit);
        
    }
  
}
