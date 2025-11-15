package com.tp.microservice.produit.presentation;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;

import com.tp.microservice.produit.application.Produit;
import com.tp.microservice.produit.application.ProduitService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("produits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProduitResource {

    @Autowired
    private ProduitService service; 

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
        List<ProduitDTO> produitsDTO = mapper.produitsToProduitDTOs(produitsEntites);
        
        return Response.ok(produitsDTO).build();
    }
    @GET
    @Path("/{id}")
    public Response getProduitById(@PathParam("id") int id) {
        try {
            // Appeler le service pour l'Entité
            Produit produit = service.getProduit(id);
            
            // Mapper l'Entité en DTO
            ProduitDTO produitDTO = mapper.mapProduitToProduitDTO(produit); 
            
            return Response.ok(produitDTO).build();
            
        } catch (NoSuchElementException e) {
            // Gérer le 404
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(e.getMessage())
                           .build();
        }
    }

    @POST
    public Response createProduit(CreationProduitDTO produitDTO) {
        try {
            // Le Resource mappe : DTO -> Entité
            Produit produit = mapper.mapCreationDTOToProduit(produitDTO);
            
            // Le Resource appelle le service (avec l'Entité)
            Produit created = service.creationProduit(produit);
            
            // Le Resource re-mappe : Entité -> DTO pour la réponse
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
            // Le Resource appelle le service pour l'Entité
            Produit produitExistant = service.getProduit(id);
            
            // Le Resource appelle le mapper pour appliquer le patch

            mapper.appliquerPatchProduit(produitExistant, produitDTO);            
            // Le Resource sauvegarde l'Entité modifiée
            Produit savedProduit = service.updateProduit(produitExistant);
            
            return Response.ok(mapper.mapProduitToProduitDTO(savedProduit)).build();
            
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