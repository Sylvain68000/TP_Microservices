package com.imt.framework.web.tuto.presentation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import com.imt.framework.web.tuto.application.Client;
import com.imt.framework.web.tuto.application.ClientService;

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
import jakarta.ws.rs.core.Response.Status;


@Path("/clients")
public class ClientResource {

    @Autowired
    private ClientService clientService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClient(){
        try {
            //appel à la logique métier
            List<Client> clients = clientService.listingClients();
            ClientMapper mapper = new ClientMapper();
            List<ClientDTO> clientsDTO = clients.stream()
                .map(mapper::mapClient)
                .collect(Collectors.toList());
            return Response.ok()
                   .entity(clientsDTO)
                   .header("Access-Control-Allow-Origin", "*")
                   .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                         .entity("Erreur lors de la récupération des clients: " + e.getMessage())
                         .build();
        }
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientById(@NotNull @PathParam("id") Integer id){
        try {
            Client client = clientService.getClient(id);
            // Mapper la réponse vers un DTO pour la sortie
            ClientDTO clientDTO = new ClientMapper().mapClient(client); 
            return Response.ok().entity(clientDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createClient(@NotNull @RequestBody ClientDTO clientDTO){
        //appel à la logique métier
        ClientMapper lm = new ClientMapper();
        Client client = lm.mapClientToEntity(clientDTO);
        clientService.createClient(client);
        return Response.status(Response.Status.CREATED).build();
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateClient(@NotNull @PathParam("id") Integer id, @NotNull @RequestBody ClientDTO clientDTO) throws Exception {
        
        Client existant = clientService.getClient(id);

        if(null == existant) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Client inconnu")
                           .build();
        }
        else {
            ClientMapper cm = new ClientMapper();
            Client client = cm.mapClientToEntity(clientDTO);
            client.setId(id);
            clientService.updateClient(client);
            return Response.noContent().build();
        }
    }

    @DELETE
    @Path("/{id}") // Correction : était "/id}"
    public Response deleteClient(@NotNull @PathParam("id") final Integer id){
        clientService.deleteClient(id);
        return Response.noContent().build();
}
    @GET
    @Path("/{clientId}/produits")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduitsClient(@NotNull @PathParam("clientId") Integer clientId) {
        try {
            Client client = clientService.getClient(clientId);
            return Response.ok()
                         .entity(client.getProduitsCommandesIds())
                         .build();
        } catch (EntityNotFoundException e) {
            return Response.status(Status.NOT_FOUND)
                         .entity("Client non trouvé: " + e.getMessage())
                         .build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                         .entity("Erreur lors de la récupération des produits du client: " + e.getMessage())
                         .build();
        }
    }

    @POST
    @Path("/{clientId}/produits/{produitId}") 
    @Produces(MediaType.APPLICATION_JSON)
    public Response lierProduitAuClient(
        @NotNull @PathParam("clientId") Integer clientId,
        @NotNull @PathParam("produitId") Integer produitId) {

        try {
            Client clientUpdated = clientService.lierProduitAuClient(clientId, produitId);
            ClientDTO dto = new ClientMapper().mapClient(clientUpdated);
            return Response.status(Status.OK)
                         .entity(dto)
                         .build(); 
        } catch (EntityNotFoundException e) {
            // Renvoie 404 si le client ou le produit n'est pas trouvé
            return Response.status(Status.NOT_FOUND)
                         .entity(e.getMessage())
                         .build();
        } catch (IllegalArgumentException e) {
            // Renvoie 400 si les paramètres sont invalides
            return Response.status(Status.BAD_REQUEST)
                         .entity(e.getMessage())
                         .build();
        } catch (Exception e) {
            e.printStackTrace(); // Pour le debug
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                         .entity("Erreur interne lors de la liaison: " + e.getMessage())
                         .build();
        }
}
}
