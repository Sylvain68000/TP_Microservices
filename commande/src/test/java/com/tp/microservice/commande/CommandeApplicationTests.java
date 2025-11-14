package com.tp.microservice.commande;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.tp.microservice.commande.application.Commande;
import com.tp.microservice.commande.application.StatusCommande;
import com.tp.microservice.commande.presentation.CommandeMapper;
import com.tp.microservice.commande.presentation.CreationCommandeDTO;

/**
 * Test pour valider la logique de CRÉATION de commande.
 * Il vérifie que le mapper transfère correctement les IDs du client et du produit,
 * et définit le statut par défaut.
 */
@SpringBootTest
class CommandeApplicationTests {

     // Valide la logique vitale de création de commande (DTO -> Entité).
    @Test
    void testMapCreationDTOToCommande_LienVital() {
        // Simule la requête entrante reçue du contrôleur (ClientResource)
        CreationCommandeDTO dto = new CreationCommandeDTO();
        dto.setNom("Test Commande");
        dto.setClientId(42); // Simule l'ID du client (l'utilisateur)
        dto.setProduits(List.of(10, 20)); // Simule les IDs de produits dans le panier
        // On crée le mapper
        CommandeMapper mapper = new CommandeMapper();

        //On exécute la méthode à tester : la conversion du DTO en Entité
        Commande resultat = mapper.mapCreationDTOToCommande(dto);

        // S'assurer que l'objet Entité a bien été créé
        assertNotNull(resultat);
        
        // Le clientId est-il bien copié sur l'Entité ?
        assertEquals(42, resultat.getClientId(), "Le clientId doit être transféré.");
        
        // La liste des IDs de produits est-elle correcte ?
        assertNotNull(resultat.getIdProduits());
        assertEquals(2, resultat.getIdProduits().size(), "La liste des IDs de produits doit contenir 2 éléments.");
        
        // Le statut par défaut est-il bien EN_ATTENTE ?
        assertEquals(StatusCommande.EN_ATTENTE, resultat.getStatut(), "Le statut doit être EN_ATTENTE après création.");
		//Le nom est-il bien copié ?
        assertEquals("Test Commande", resultat.getNom(), "Le nom doit être correctement copié.");
    }

}
