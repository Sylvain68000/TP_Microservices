package com.tp.microservice.produit.presentation;

import com.tp.microservice.produit.application.Produit;
import com.tp.microservice.produit.application.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.NoSuchElementException;

@Path("produits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProduitResource {

    @Autowired
    private ProduitService service; // Le service (qui renvoie des Entités)

    @Autowired
    private ProduitMapper mapper; 

    @GET
    public Response getProduits(@QueryParam("idProduits") List<Integer> idProduits) {
        
        List<Produit> produitsEntites; // On récupère les Entités

        if (idProduits == null || idProduits.isEmpty()) {
            // Cas 1 : GET /api/produits (Tout lister)
            produitsEntites = service.listingProduits();
        } else {
            // Cas 2 : GET /api/produits?idProduits=... (Spécifique)
            produitsEntites = service.getProduitsByIds(idProduits);
        }

        // Le Resource fait le mapping
        List<ProduitDTO> produitsDTO = mapper.mapProduitToProduitDTO(produitsEntites);
        
        return Response.ok(produitsDTO)
                       .header("Access-Control-Allow-Origin", "*")
                       .build();
    }

    @POST
    public Response createProduit(CreationProduitDTO produitDTO) {
        try {
            // 1. Le Resource mappe : DTO -> Entité
            Produit produit = mapper.mapCreationDTOToProduit(produitDTO);
            
            // 2. Le Resource appelle le service (avec l'Entité)
            Produit created = service.creationProduit(produit);
            
            // 3. Le Resource re-mappe : Entité -> DTO pour la réponse
            return Response.status(Response.Status.CREATED)
                           .entity(mapper.mapProduitToProduitDTO(created))
                           .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PATCH
    @Path("/{id}")
    public Response updateProduit(@PathParam("id") Integer id, UpdateProduitDTO produitDTO) {
        try {
            // 1. Le Resource appelle le service pour l'Entité
            Produit produitExistant = service.getProduit(id);
            
            // 2. Le Resource appelle le mapper pour appliquer le patch
            // (Nous devons créer cette méthode dans le mapper)
            mapper.applyPatchToProduit(produitExistant, produitDTO);
            
            // 3. Le Resource sauvegarde l'Entité modifiée
            Produit savedProduit = service.updateProduit(produitExistant);
            
            return Response.ok(mapper.mapToDTO(savedProduit)).build();
            
        } catch (NoSuchElementException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduit(@PathParam("id") final Integer id){
        try {
            service.deleteProduit(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}