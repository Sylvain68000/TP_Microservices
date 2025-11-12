package com.tp.microservice.client.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tp.microservice.client.application.Client;
import com.tp.microservice.client.application.CommandeDAO;


@Component
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
                
        return client;
    
    }

public List<CommandeDTO> mapCommandeDAOToCommandeDTO(List<CommandeDAO> commandes) {
        if (commandes == null) {
            return new ArrayList<>();
        }
        return commandes.stream()
                .map(c -> new CommandeDTO(c.getId(), c.getNom(), c.getProduits(), c.getStatut(), c.getClientId()))
                .collect(Collectors.toList());
    }

        public void appliquerPatchClient(Client clientExistant, UpdateClientDTO ClientDTO) {
        if (ClientDTO.getNom() != null) {
            clientExistant.setNom(ClientDTO.getNom());
        }
        if (ClientDTO.getPrenom() != null) {
            clientExistant.setPrenom(ClientDTO.getPrenom());
        }
        if (ClientDTO.getMail() != null) {
            clientExistant.setMail(ClientDTO.getMail());
        }
        if (ClientDTO.getTelephone() != null) {
            clientExistant.setTelephone(ClientDTO.getTelephone());
        }
    }


}