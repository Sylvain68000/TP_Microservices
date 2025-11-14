package com.tp.microservice.produit;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import com.tp.microservice.produit.application.Produit;
import com.tp.microservice.produit.application.ProduitService;
import com.tp.microservice.produit.infrastructure.ProduitRepository;


@SpringBootTest
class ProduitApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
    private ProduitRepository produitRepository; // "faux" repository
	

    @InjectMocks
    private ProduitService produitService;
	@Test
	//Vérifier que la méthode getProduit(id) fonctionne avec un ID correctement quand la donnée est trouvée.
    void testGetProduit_OK() { 
        // On creer le produit de test
        Produit produitDeTest = new Produit();
        produitDeTest.setId(1);
        produitDeTest.setNom("Test Marteau");
        
        // Dit au "faux" repository quoi faire
        when(produitRepository.findById(1)).thenReturn(Optional.of(produitDeTest));

        // On exécute la vraie méthode du service
        Produit resultat = produitService.getProduit(1);

        // Vérifie qu'il a bien renvoyé quelque chose
        assertNotNull(resultat);
        assertEquals("Test Marteau", resultat.getNom());
        verify(produitRepository).findById(1); // Vérifie que la méthode a été appelée le bon ID
    }

	@Test
	  //Vérifier que la méthode getProduit(id) gère correctement le cas où l'ID n'existe pas, en renvoyant une erreur 404
	  void testGetProduit_NotFound() {
        // On programme le Faux repository : "Si on me demande l'ID 99 (qui n'existe pas), je renvoie un Optional.empty()."
        when(produitRepository.findById(99)).thenReturn(Optional.empty());

        // On vérifie qu'une exception 'NoSuchElementException' est bien lancée
        assertThrows(NoSuchElementException.class, () -> {
            produitService.getProduit(99);
        });

        verify(produitRepository).findById(99); // Vérifie que la méthode a été appelée
    }

	   @Test
		//Vérifier que la méthode getProduit(id) fonctionne avec plusieurs ID correctement quand les données sont trouvées.
  		void testGetProduitsByIds() {
		//on veut les IDs 1 et 2 simulation de GET /api/produits?idProduits=1&idProduits=2
		List<Integer> idsDemandes = List.of(1, 2);
		List<Produit> produitsTrouves = List.of(new Produit(), new Produit()); // On crée les deux Entités qui seront renvoyées par le faux repository.

		// On programme le Faux repository : "Si on me demande une recherche par liste (findByIdIn), je renvoie les deux produits."
		when(produitRepository.findByIdIn(idsDemandes))
			 .thenReturn(produitsTrouves); 

        // On exécute la vraie méthode du service (qui doit renvoyer une List de Produits)
		List<Produit> resultat = produitService.getProduitsByIds(idsDemandes);

		// Vérifie qu'on a bien reçu les deux produits.
		assertNotNull(resultat);
		assertEquals(2, resultat.size()); 
	
		// On vérifie que le service a appeler FinById du repository avec les bons IDs d'un seul coup
		verify(produitRepository).findByIdIn(idsDemandes);
			}

}
