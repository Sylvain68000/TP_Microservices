package com.tp.microservice.client.application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tp.microservice.client.infrastructure.ClientRepository;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    public void creationClient(Client client)
    {
        clientRepository.save(client);
    }
    
    public List<CommandeDAO> getCommandes(List<Integer> idCommandes) {
	//déclaration de l'objet permettant de déclencher un appel REST
	jakarta.ws.rs.client.Client client = ClientBuilder.newClient();
	//on paramètre le lien d'appel, notre module Commande
	WebTarget target = client.target("http://localhost:8081/api/commandes");
	// Ajout des query params représentant les identifiants de commandes
	for (Integer id : idCommandes) {
		target = target.queryParam("idCommandes", id);
	}
	Response response = target.request(MediaType.APPLICATION_JSON).get();
	//ici, cela nous permet de convertir directement la réponse du webservice en objet ClientDAO
	List<CommandeDAO> commandes = response.readEntity(new GenericType<List<CommandeDAO>>() {});
	response.close();
	client.close();
	return commandes;
	}
}   
