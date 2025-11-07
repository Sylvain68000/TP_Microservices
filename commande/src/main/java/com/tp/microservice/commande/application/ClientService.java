package com.imt.framework.web.tuto.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imt.framework.web.tuto.infrastructure.ClientRepository;
import com.imt.framework.web.tuto.infrastructure.ProduitRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> listingClients(){
        return clientRepository.findAll();
    }

    public Client getClient(int id){
        Optional<Client> client = clientRepository.findById(id);
        return client.get();
    }
    public void updateClient(Client client) {
        clientRepository.save(client);
    }
    public void deleteClient(int id){
        clientRepository.deleteById(id);
    }
    public void createClient(Client client)
    {
        clientRepository.save(client);
    }

    @Autowired 
    private ProduitRepository produitRepository; // Pour vérifier l'existence du produit.

    // Méthode vitale demandée par le TP
    @org.springframework.transaction.annotation.Transactional
    public Client lierProduitAuClient(Integer clientId, Integer produitId) {
        if (clientId == null || produitId == null) {
            throw new IllegalArgumentException("Les IDs du client et du produit ne peuvent pas être null");
        }

        // Vérifier l'existence du produit d'abord
        if (!produitRepository.existsById(produitId)) {
            throw new EntityNotFoundException("Produit non trouvé: " + produitId);
        }

        // Charger le client avec ses produits
        Client client = clientRepository.findById(clientId)
            .orElseThrow(() -> new EntityNotFoundException("Client non trouvé: " + clientId));

        // Initialiser la collection si nécessaire
        if (client.getProduitsCommandesIds() == null) {
            client.setProduitsCommandesIds(new ArrayList<>());
        }

        // Lier client au produit si pas déjà lié
        if (!client.getProduitsCommandesIds().contains(produitId)) {
            client.getProduitsCommandesIds().add(produitId);
            client = clientRepository.save(client);
        }

        return client;
    }
}
