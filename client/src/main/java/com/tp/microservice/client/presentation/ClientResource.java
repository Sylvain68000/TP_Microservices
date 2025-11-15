package com.tp.microservice.client.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;

import com.tp.microservice.client.application.Client;
import com.tp.microservice.client.application.ClientService;
import com.tp.microservice.client.application.CommandeDAO;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("clients")
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON)
public class ClientResource {

	// permet à Spring de récupérer l'instance ClientService afin de manipuler
	// son contenu
	@Autowired
	private ClientService service;

	@Autowired
 	private ClientMapper mapper;

	@GET
	@Produces("application/json")
	public List<ClientDTO> getClients() {
		List<Client> clientsBdd = service.listingClients();
		List<ClientDTO> clientRetournees = new ArrayList<>();

		for (Client c : clientsBdd) {
 		//Mappe le client (Nom, Prénom, etc.)
		ClientDTO clientAAjouter = mapper.mapClientToClientDTO(c);

            // Récupérer les données BRUTES (DAO) du service
			List<CommandeDAO> commandesDAO = service.getCommandes( c.getId() );
            
            // Traduire les DAO en DTO (via le mapper)
            List<CommandeDTO> commandesDTO = mapper.mapCommandeDAOToCommandeDTO(commandesDAO);

            // Mettre les DTO (traduits) dans l'objet final
			clientAAjouter.setCommandes(commandesDTO);  

 			clientRetournees.add(clientAAjouter);
 		}
 		return clientRetournees;
 	}

	@GET
    @Path("/{id}")
    public Response getClientById(@PathParam("id") int id) {
        try {
            Client client = service.getClient(id);
            
            ClientDTO clientDTO = mapper.mapClientToClientDTO(client);
            
            List<CommandeDAO> commandesDAO = service.getCommandes( client.getId() );
            List<CommandeDTO> commandesDTO = mapper.mapCommandeDAOToCommandeDTO(commandesDAO);
            clientDTO.setCommandes(commandesDTO);
            
            return Response.ok(clientDTO).build();
            
        } catch (NoSuchElementException e) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(e.getMessage())
                           .build();
        }
    }
	
	@POST
	// permet de dire que le webservice attend un json avec la requête
	@Consumes("application/json")
	public void creationClient(CreationClientDTO clientDTO) {
		Client clientToSave = new ClientMapper().mapClientDTOToClient(clientDTO);
		service.creationClient(clientToSave);
	}

    @PATCH
    @Path("/{id}")
    public Response updateClient(@PathParam("id") int id, UpdateClientDTO clientDTO) {
        try {
            // Récupérer l'entité
            Client clientExistant = service.getClient(id);
            
            // Appliquer le patch 
            mapper.appliquerPatchClient(clientExistant, clientDTO);
            
            // Sauvegarder
            Client clientMisAJour = service.updateClient(clientExistant);
            
            return Response.ok(clientMisAJour).build();
            
        } catch (NoSuchElementException e) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(e.getMessage())
                           .build();
        }
    }


    @DELETE
    @Path("/{id}")
    public Response deleteClient(@PathParam("id") int id) {
        try {
            service.deleteClient(id); // (Le service gère le 404)
            return Response.noContent().build(); 
        } catch (NoSuchElementException e) {
             return Response.status(Response.Status.NOT_FOUND)
                           .entity(e.getMessage())
                           .build();
        }
    }

}
