package com.tp.microservice.client;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tp.microservice.client.application.Client;
import com.tp.microservice.client.application.ClientService;
import com.tp.microservice.client.application.CommandeDAO;
import com.tp.microservice.client.infrastructure.ClientRepository;
import com.tp.microservice.client.presentation.ClientDTO;
import com.tp.microservice.client.presentation.ClientMapper;
import com.tp.microservice.client.presentation.UpdateClientDTO;

// Utilise Mockito pour isoler les dépendances BDD (Repository)
@ExtendWith(MockitoExtension.class)
class ClientApplicationTests {

    @Mock
    private ClientRepository clientRepository; // Faux repository pour simuler la BDD

    @InjectMocks
    private ClientService clientService;
    
    private ClientMapper mapper = new ClientMapper();


    // Valider que la méthode getCommandes() ne plante pas avec un appel API si le clientId est null ou zéro, et renvoie une liste sécurisée si le client n'a pas de commandes


    @Test
    void testGetCommandes_IDsNullOuZero() {
        Integer idClientNull = null;
        Integer idClientZero = 0;
        
        // On teste le service avec des IDs problématiques
        // Nous savons que clientService.getCommandes(id) renvoie List<CommandeDAO>
        List<CommandeDAO> resultat1 = clientService.getCommandes(idClientNull); 
        List<CommandeDAO> resultat2 = clientService.getCommandes(idClientZero); 

        // Le résultat doit être une liste vide et non une NullPointerException.
        assertNotNull(resultat1, "Le résultat pour un ID null ne doit pas être null.");
        assertEquals(0, resultat1.size(), "Doit renvoyer une liste vide si l'ID client est null.");
        
        assertNotNull(resultat2, "Le résultat pour un ID zéro ne doit pas être null.");
        assertEquals(0, resultat2.size(), "Doit renvoyer une liste vide si l'ID client est zéro (sauf si l'API est démarrée).");
    }


    //Vérifie que la tentative de suppression d'un client inexistant lève l'exception 404.
    @Test
    void testDeleteClient_NotFound() {
        Integer idInexistant = 999;
        
        // Préparation: Simule que le repository ne trouve rien
        when(clientRepository.findById(idInexistant)).thenReturn(Optional.empty());

        // Vérification: On s'attend à ce que getClient (appelé par deleteClient) plante avec l'exception 404
        assertThrows(NoSuchElementException.class, () -> {
            clientService.deleteClient(idInexistant);
        }, "La suppression d'un client inexistant doit lever une NoSuchElementException.");
        
        // S'assurer que le service a tenté de vérifier l'existence
        verify(clientRepository, times(1)).findById(idInexistant);
        // S'assurer que la suppression finale n'a pas été appelée
        verify(clientRepository, never()).delete(any(Client.class));
    }
    
    // Valider la méthode appliquerPatchClient(). Elle doit mettre à jour uniquement les champs non-null (mail), tout en conservant les autres (nom, tel).

    @Test
    void testMapper_AppliquerPatchClient_MiseAJourPartielle() {

        Client clientExistant = new Client();
        clientExistant.setNom("Ancien Nom");
        clientExistant.setTelephone("000");
        clientExistant.setMail("old@test.com");
        
        UpdateClientDTO patchDTO = new UpdateClientDTO();
        patchDTO.setMail("nouveau@test.com"); 

        mapper.appliquerPatchClient(clientExistant, patchDTO);

        // Mail DOIT être mis à jour
        assertEquals("nouveau@test.com", clientExistant.getMail(), "Le mail doit être mis à jour.");
        // Nom NE DOIT PAS être écrasé
        assertEquals("Ancien Nom", clientExistant.getNom(), "Le nom doit être conservé.");
        // Téléphone NE DOIT PAS être écrasé
        assertEquals("000", clientExistant.getTelephone(), "Le téléphone doit être conservé.");
    }
    
    //Valide que le mapping de lecture Client -> DTO transfère tous les champs.
    @Test
    void testMapper_mapClientToClientDTO_Success() {
       
        Client clientExistant = new Client();
        clientExistant.setId(1);
        clientExistant.setNom("Dupont");
        clientExistant.setMail("jean.dupont@test.com");
        
        
        ClientDTO resultat = mapper.mapClientToClientDTO(clientExistant);

        
        assertEquals(1, resultat.getId(), "L'ID doit être transféré.");
        assertEquals("Dupont", resultat.getNom());
        assertEquals("jean.dupont@test.com", resultat.getMail());
    }

}
