package com.tp.microservice.client.presentation;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.tp.microservice.client.application.Client;
import com.tp.microservice.client.application.ClientService;
import com.tp.microservice.client.application.CommandeDAO;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("clients")
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON)
public class ClientResource {

	// permet à Spring de récupérer l'instance ClientService afin de manipuler
	// son contenu
	@Autowired
	private ClientService service;

	@GET
	@Produces("application/json")
	public List<ClientDTO> getClients() {
		//ne pas oublier de mapper les données :)
		ClientMapper em = new ClientMapper();
		//on récupère toutes les entreprises
		List<Client> clientsBdd = service.listingClients();
		//cette liste nous sert d'objet de retour
		List<ClientDTO> clientRetournees = new ArrayList<>();
		//dans cette partie, on transforme les données en EntrepriseDTO
		for (Client c : clientsBdd) {
			ClientDTO clientAAjouter = new ClientDTO();
			clientAAjouter = em.mapClientToClientDTO(c);
			//partie Commandes, on va interroger le module Commande
			//et mettre à dispo les infos commandes dans chaque client
			if (c.getIdCommandes() != null && !c.getIdCommandes().isEmpty()) {
				//ici on fait l'appel au module Commande via la couche presentation
				List<CommandeDAO> commandes = service.getCommandes(c.getIdCommandes());
				//on map les commandes dans l'objet de retour associé et on l'ajoute à son client
				clientAAjouter.setCommandes(em.mapCommandeDAOToCommandeDTO(commandes));
			}
			//on ajoute le client mappé dans le résultat de la requête
			clientRetournees.add(clientAAjouter);
		}
		return clientRetournees;
	}

	// verbe de création
	@POST
	// permet de dire que le webservice attend un json avec la requête
	@Consumes("application/json")
	public void creationClient(CreationClientDTO clientDTO) {
		Client clientToSave = new ClientMapper().mapClientDTOToClient(clientDTO);
		service.creationClient(clientToSave);
	}

}
