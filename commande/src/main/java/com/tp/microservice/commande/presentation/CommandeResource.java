package com.tp.microservice.commande.presentation;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tp.microservice.commande.application.Commande;
import com.tp.microservice.commande.application.CommandeService;
import com.tp.microservice.commande.application.ProduitDAO;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
	public List<CommandeDTO> getCommandes() {
		//on récupère toutes les entreprises
		List<Commande> commandesBdd = service.listingCommandes();
		//cette liste nous sert d'objet de retour
		List<CommandeDTO> commandeRetournees = new ArrayList<>();
		//dans cette partie, on transforme les données en CommandeDTO
		for (Commande c : commandesBdd) {
            // Récupérer les produits bruts (de l'API)
		    List<ProduitDAO> produitsBruts = new ArrayList<>();
            if (c.getIdProduits() != null && !c.getIdProduits().isEmpty()) {
            produitsBruts = service.getProduits(c.getIdProduits());
        }
		// On donne au mapper la commande ET les produits bruts
            CommandeDTO commandeAAjouter = mapper.mapCommandetoCommandeDTO(c, produitsBruts);
            // Ajouter le DTO assemblé à la liste
            commandeRetournees.add(commandeAAjouter);
		}
		return commandeRetournees;
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
}