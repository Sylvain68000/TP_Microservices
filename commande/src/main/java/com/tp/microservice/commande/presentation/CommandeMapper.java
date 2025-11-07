package com.tp.microservice.commande.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.tp.microservice.commande.application.Commande;
import com.tp.microservice.commande.application.ProduitDAO;
import com.tp.microservice.commande.presentation.ProduitDTO;

public class CommandeMapper {

    public CommandeDTO mapCommandetoCommandeDTO(Commande commande) {
        CommandeDTO CommandeDTO = new CommandeDTO();
        CommandeDTO.setId(commande.getId());
        CommandeDTO.setNom(commande.getNom());
        CommandeDTO.setStatut(commande.getStatut());
        
        return CommandeDTO;
    }

    public Commande mapCommandeDTOtoCommande(CommandeDTO commandeDTO) {
        Commande commande = new Commande();
        commande.setId(commandeDTO.getId());
        commande.setNom(commandeDTO.getNom());
        commande.setStatut(commandeDTO.getStatut());
        
        return commande;
    }

    public List<ProduitDTO> mapProduitDAOToProduitDTO(List<ProduitDAO> produits) {
        if (produits == null) {
            return new ArrayList<>();
        }
        return produits.stream()
                .map(p -> new ProduitDTO(p.getId(), p.getNom(), p.getPrix()))
                .collect(Collectors.toList());
    }

}

