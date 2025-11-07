package com.tp.microservice.commande.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.tp.microservice.commande.infrastructure.CommandeRepository;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Service
public class  CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    public List<Commande> listingCommandes(){
        return commandeRepository.findAll();
    }

    public Commande getCommande(int id){
        Optional<Commande> commande = commandeRepository.findById(id);
        return commande.get();
    }
    public void updateCommande(Commande commande) {
        commandeRepository.save(commande);
    }
    public void deleteCommande(int id){
        commandeRepository.deleteById(id);
    }
    public void creationCommande(Commande commande)
    {
        commandeRepository.save(commande);
    }

    public List<ProduitDAO> getProduits(List<Integer> idProduits) {
	//déclaration de l'objet permettant de déclencher un appel REST
	jakarta.ws.rs.client.Client client = ClientBuilder.newClient();
	//on paramètre le lien d'appel, notre module Commande
	WebTarget target = client.target("http://localhost:8082/api/produits");
	// Ajout des query params représentant les identifiants de produits
	for (Integer id : idProduits) {
		target = target.queryParam("idProduits", id);
	}
	Response response = target.request(MediaType.APPLICATION_JSON).get();
	//ici, cela nous permet de convertir directement la réponse du webservice en objet ClientDAO
	List<ProduitDAO> produits = response.readEntity(new GenericType<List<ProduitDAO>>() {});
	response.close();
	client.close();
	return produits;
	}
    
}
