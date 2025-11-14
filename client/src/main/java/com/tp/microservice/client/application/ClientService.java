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
    public Client updateClient(Client client) {
         return clientRepository.save(client);
    }
    public void deleteClient(int id){
        Client c = this.getClient(id);
        clientRepository.delete(c);
    }
    public Client creationClient(Client client)
    {
         return clientRepository.save(client);
    }
    
    public List<CommandeDAO> getCommandes(Integer clientId) { 
       jakarta.ws.rs.client.Client client = ClientBuilder.newClient();
        
        WebTarget target = client.target("http://localhost:8081/api/commandes/client/" + clientId);
         
        Response response = null;
        try {
        response = target.request(MediaType.APPLICATION_JSON).get();
            if (response.getStatus() == 200) {
               
                return response.readEntity(new GenericType<List<CommandeDAO>>() {});
            }
            return List.of();
        } catch (Exception e) {
            System.err.println("ERREUR: Impossible de contacter le service commande. Message: " + e.getMessage());
            return List.of();
        } finally {
            if (response != null) response.close();
            client.close();
        }
    }
}   
