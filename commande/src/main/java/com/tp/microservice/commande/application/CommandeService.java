package com.tp.microservice.commande.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.tp.microservice.commande.infrastructure.CommandeRepository;

import jakarta.ws.rs.client.Client;
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
    public Commande creationCommande(Commande commande)
    {
        return commandeRepository.save(commande);
        
    }

public List<ProduitDAO> getProduits(List<Integer> idProduits) {
        if (idProduits == null || idProduits.isEmpty()) {
            return List.of(); // 'List.of()' crée une liste vide
        }
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8082/api/produits");
        
        for (Integer id : idProduits) {
            target = target.queryParam("idProduits", id);
        }

        Response response = null;
        try {
            // --- C'est la zone protégée ---
            response = target.request(MediaType.APPLICATION_JSON).get();
            
            if (response.getStatus() == 200) {
                return response.readEntity(new GenericType<List<ProduitDAO>>() {});
            }
            return List.of(); 
            
        } catch (Exception e) {
            // --- C'est ce qui attrape le "Connection refused" ---
            System.err.println("ERREUR: Impossible de contacter le service produit. Message: " + e.getMessage());
            return List.of(); // Renvoyer une liste vide au lieu de planter
            
        } finally {
            // Toujours fermer les ressources
            if (response != null) {
                response.close();
            }
            client.close();
        }
    }
    
}
