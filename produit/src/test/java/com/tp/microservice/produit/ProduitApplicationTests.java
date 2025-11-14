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
    void testGetProduit_OK() {
        // 1. Préparation (Arrange)
        Produit produitDeTest = new Produit();
        produitDeTest.setId(1);
        produitDeTest.setNom("Test Marteau");
        
        // Dit au "faux" repository quoi faire
        when(produitRepository.findById(1)).thenReturn(Optional.of(produitDeTest));

        // 2. Action (Act)
        Produit resultat = produitService.getProduit(1);

        // 3. Vérification (Assert)
        assertNotNull(resultat);
        assertEquals("Test Marteau", resultat.getNom());
        verify(produitRepository).findById(1); // Vérifie que la méthode a été appelée
    }

	@Test
	  void testGetProduit_NotFound() {
        // 1. Préparation (Arrange)
        // Dit au "faux" repository de ne rien renvoyer
        when(produitRepository.findById(99)).thenReturn(Optional.empty());

        // 2. Action & 3. Vérification (Assert)
        // On vérifie qu'une exception 'NoSuchElementException' est bien lancée
        assertThrows(NoSuchElementException.class, () -> {
            produitService.getProduit(99);
        });

        verify(produitRepository).findById(99); // Vérifie que la méthode a été appelée
    }

	   @Test
  		void testGetProduitsByIds() {
		// --- 1. PRÉPARATION (Arrange) ---
		List<Integer> idsDemandes = List.of(1, 2);
		List<Produit> produitsTrouves = List.of(new Produit(), new Produit());

		// On programme le "faux frigo"
		when(produitRepository.findByIdIn(idsDemandes))
			 .thenReturn(produitsTrouves); 

		// --- 2. ACTION (Act) ---
        // 'resultat' est bien List<Produit>, comme dans votre service
		List<Produit> resultat = produitService.getProduitsByIds(idsDemandes);

		// --- 3. VÉRIFICATION (Assert) ---
		assertNotNull(resultat);
		assertEquals(2, resultat.size()); 
	
		// On vérifie que le service a appelé le frigo
		verify(produitRepository).findByIdIn(idsDemandes);
			}

}
