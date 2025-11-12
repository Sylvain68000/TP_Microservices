package com.tp.microservice.commande.presentation;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;

import com.tp.microservice.commande.application.Commande;
import com.tp.microservice.commande.application.CommandeService;
import com.tp.microservice.commande.application.ProduitDAO;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("commandes")
@Produces(MediaType.APPLICATION_JSON) 
@Consumes(MediaType.APPLICATION_JSON)
public class CommandeResource {
    	// permet à Spring de récupérer l'instance ClientService afin de manipuler
	// son contenu
	@Autowired
	private CommandeService service;

    @Autowired
    private CommandeMapper mapper;

	@GET
	@Produces("application/json")
 	public Response getCommandes(@QueryParam("idCommandes") List<Integer> idCommandes) {

		List<Commande> commandesBdd;

        // 3. LOGIQUE DE FILTRAGE
        if (idCommandes == null || idCommandes.isEmpty()) {
            // Cas 1 : L'utilisateur demande TOUTES les commandes (ex: Postman)
			commandesBdd = service.listingCommandes();
        } else {
            // Cas 2 : Le ClientService demande des IDs spécifiques
            // (Nous utilisons la boucle N+1, comme vous le préférez)
            commandesBdd = new ArrayList<>();
            for (Integer id : idCommandes) {
                try {
                    // (On utilise getCommande que vous avez déjà corrigé)
                    commandesBdd.add(service.getCommande(id));
                } catch (NoSuchElementException e) {
                    // Ignore l'ID si non trouvé, ne plante pas la boucle
                }
            }
        }
        
		List<CommandeDTO> commandeRetournees = new ArrayList<>();
		for (Commande c : commandesBdd) {
			List<ProduitDAO> produitsBruts = new ArrayList<>();
            // (On suppose que EAGER + try-catch sont faits)
 			if (c.getIdProduits() != null && !c.getIdProduits().isEmpty()) {
			produitsBruts = service.getProduits(c.getIdProduits());
		  }
		CommandeDTO commandeAAjouter = mapper.mapCommandetoCommandeDTO(c, produitsBruts);
 			commandeRetournees.add(commandeAAjouter);
 		}
 		return Response.ok(commandeRetournees).build();
 	}
	// verbe de création
	@POST
	// permet de dire que le webservice attend un json avec la requête
	@Consumes("application/json")
    public Response creationCommande(CreationCommandeDTO commandeDTO) { //  RENVOYER UNE RESPONSE
    
    // APPELER LA MÉTHODE DU MAPPER
    // (Celle qui convertit CreationCommandeDTO -> Commande)
    Commande commandeAsauver = mapper.mapCreationDTOToCommande(commandeDTO); 
    
    // Récupérer l'objet sauvegardé (il a l'ID BDD)
    Commande commandeSauvegardee = service.creationCommande(commandeAsauver);
    
    // 5. Renvoyer un statut "201 Created" avec l'objet
    return Response.status(Response.Status.CREATED)
                   .entity(commandeSauvegardee)
                   .build();

}
	@GET
    @Path("/{id}")
    public Response getCommandeById(@PathParam("id") int id) {
        try {
            // 1. Récupérer l'entité
            Commande commande = service.getCommande(id);
            
            // 2. Récupérer les produits (comme dans la boucle GET)
            List<ProduitDAO> produitsBruts = new ArrayList<>();
            if (commande.getIdProduits() != null && !commande.getIdProduits().isEmpty()) {
                produitsBruts = service.getProduits(commande.getIdProduits());
            }
            
            // 3. Mapper et renvoyer le DTO complet
            CommandeDTO commandeDTO = mapper.mapCommandetoCommandeDTO(commande, produitsBruts);
            return Response.ok(commandeDTO).build();
            
        } catch (NoSuchElementException e) {
            // Géré si service.getCommande(id) échoue
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(e.getMessage())
                           .build();
        }
    }
	
	@DELETE
    @Path("/{id}")
    public Response deleteCommande(@PathParam("id") int id) {
        try {
            service.deleteCommande(id); // (Le service gère le 404)
            return Response.noContent().build(); 
        } catch (NoSuchElementException e) {
             return Response.status(Response.Status.NOT_FOUND)
                           .entity(e.getMessage())
                           .build();
        }
    }

	@GET
    @Path("/client/{clientId}") // <-- Le chemin que vous avez testé
    public Response getCommandesParClientId(@PathParam("clientId") Integer clientId) {
        
        // 1. Appelle le service pour les Entités (filtrées)
        List<Commande> commandesBdd = service.getCommandesPourClient(clientId);
        
        // 2. Logique d'assemblage (comme dans votre méthode 'getCommandes')
		List<CommandeDTO> commandeRetournees = new ArrayList<>();
		for (Commande c : commandesBdd) {
			List<ProduitDAO> produitsBruts = service.getProduits(c.getIdProduits());
			CommandeDTO commandeAAjouter = mapper.mapCommandetoCommandeDTO(c, produitsBruts);
			commandeRetournees.add(commandeAAjouter);
		}		
		return Response.ok(commandeRetournees).build();
    }

}