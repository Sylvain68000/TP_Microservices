package com.tp.microservice.client.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.tp.microservice.client.application.Client;
import com.tp.microservice.client.application.CommandeDAO;

public class ClientMapper {


    public ClientDTO mapClientToClientDTO(Client client) {

        ClientDTO clientDTO = new ClientDTO(); // On utilise le constructeur vide

        // On remplit l'objet avec les setters
        clientDTO.setId(client.getId());
        clientDTO.setNom(client.getNom());
        clientDTO.setPrenom(client.getPrenom()); 
        clientDTO.setMail(client.getMail());      
        clientDTO.setTelephone(client.getTelephone());

        return clientDTO;
    }

    public Client mapClientDTOToClient(CreationClientDTO clientDTO) {
        Client client = new Client();
        client.setNom(clientDTO.getNom());
        client.setPrenom(clientDTO.getPrenom());
        client.setMail(clientDTO.getMail());
        client.setTelephone(clientDTO.getTelephone());
        client.setIdCommandes(clientDTO.getIdCommandes());
        
        return client;
    
    }

public List<CommandeDTO> mapCommandeDAOToCommandeDTO(List<CommandeDAO> commandes) {
        if (commandes == null) {
            return new ArrayList<>();
        }
        return commandes.stream()
                .map(c -> new CommandeDTO(c.getId(), c.getNom(), c.getProduits()))
                .collect(Collectors.toList());
    }


}