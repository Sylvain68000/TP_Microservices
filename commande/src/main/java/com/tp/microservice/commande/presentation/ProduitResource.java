package com.imt.framework.web.tuto.presentation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import com.imt.framework.web.tuto.application.Produit;
import com.imt.framework.web.tuto.application.ProduitService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
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

@Path("/produits")
public class ProduitResource {

    @Autowired
    private ProduitService produitService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduit(){
        try {
            //appel à la logique métier
            List<Produit> produits = produitService.listingProduits();
            ProduitMapper mapper = new ProduitMapper();
            List<ProduitDTO> produitsDTO = produits.stream()
                .map(mapper::mapToDTO)
                .collect(Collectors.toList());
            return Response.ok()
                   .entity(produitsDTO)
                   .header("Access-Control-Allow-Origin", "*")
                   .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                         .entity("Erreur lors de la récupération des produits: " + e.getMessage())
                         .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduit(@NotNull @RequestBody ProduitDTO produitDTO){
        try {
            //appel à la logique métier
            ProduitMapper mapper = new ProduitMapper();
            Produit produit = mapper.mapProduit(produitDTO);
            Produit created = produitService.createProduit(produit);
            return Response.status(Response.Status.CREATED)
                         .entity(mapper.mapToDTO(created))
                         .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                         .entity("Erreur lors de la création du produit: " + e.getMessage())
                         .build();
        }
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateProduit(@NotNull @PathParam("id") Integer id, @NotNull @RequestBody ProduitDTO produitDTO) {
        try {
            // getProduit va lancer EntityNotFoundException si le produit n'existe pas
            produitService.getProduit(id);
            
            ProduitMapper mapper = new ProduitMapper();
            Produit updated = mapper.mapProduit(produitDTO);
            updated.setId(id);
            Produit savedProduit = produitService.updateProduit(updated);
            return Response.ok()
                         .entity(mapper.mapToDTO(savedProduit))
                         .build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                         .entity("Produit inconnu: " + e.getMessage())
                         .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                         .entity("Erreur lors de la mise à jour: " + e.getMessage())
                         .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduit(@NotNull @PathParam("id") final Integer id){
        produitService.deleteProduit(id);
        return Response.noContent().build();
    }
}
