package com.tp.microservice.commande.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.tp.microservice.commande.application.Commande;
import com.tp.microservice.commande.application.ProduitDAO;
import com.tp.microservice.commande.application.StatusCommande;

@Component
public class CommandeMapper {

    public CommandeDTO mapCommandetoCommandeDTO(Commande commande, List<ProduitDAO> produitsBruts) {
        CommandeDTO CommandeDTO = new CommandeDTO();
        CommandeDTO.setId(commande.getId());
        CommandeDTO.setNom(commande.getNom());
        CommandeDTO.setStatut(commande.getStatut());
        CommandeDTO.setProduits( mapProduitDAOToProduitDTO(produitsBruts) );
        CommandeDTO.setClientId( commande.getClientId() );

        return CommandeDTO;
    }


public Commande mapCreationDTOToCommande(CreationCommandeDTO commandeDTO) {
        Commande commande = new Commande();
        commande.setNom(commandeDTO.getNom());
        
        commande.setIdProduits(commandeDTO.getProduits() ); 
        
        commande.setStatut(StatusCommande.EN_ATTENTE); 
        commande.setClientId(commandeDTO.getClientId());
        
        return commande;
    }

    public List<ProduitDTO> mapProduitDAOToProduitDTO(List<ProduitDAO> produits) {
        if (produits == null) {
            return new ArrayList<>();
        }
        return produits.stream()
                .map(p -> new ProduitDTO(p.getId(), p.getNom(), p.getDescription(), p.getPrix()))
                .collect(Collectors.toList());
    }

}

