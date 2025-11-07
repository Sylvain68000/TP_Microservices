package com.imt.framework.web.tuto.presentation;

import com.imt.framework.web.tuto.application.Client;

public class ClientMapper {


    // Mappe l'entité de la Couche Métier vers le DTO de la Couche Présentation
    public ClientDTO mapClient(Client client) {
        ClientDTO clientDTO = new ClientDTO();

        clientDTO.setId(client.getId()); // On garde l'ID pour la réponse
        clientDTO.setNom(client.getNom());
        clientDTO.setPrenom(client.getPrenom());
        clientDTO.setMail(client.getMail());
        clientDTO.setTelephone(client.getTelephone());
        
         if (client.getProduitsCommandesIds() != null) {
            clientDTO.setProduitsCommandesIds(client.getProduitsCommandesIds());
        }

        return clientDTO;
    }
    
    // Mappe le DTO de la Couche Présentation vers l'Entité de la Couche Métier
    public Client mapClientToEntity(ClientDTO clientDTO) {
        Client client = new Client();
        
        // L'ID est souvent ignoré lors de la création (POST)
        client.setId(clientDTO.getId()); 
        client.setNom(clientDTO.getNom());
        client.setPrenom(clientDTO.getPrenom());
        client.setMail(clientDTO.getMail());
        client.setTelephone(clientDTO.getTelephone());
        
        return client;
    }


}