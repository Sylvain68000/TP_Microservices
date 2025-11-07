package com.tp.microservice.produit.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.tp.microservice.produit.application.Produit;




@Component
public interface ProduitRepository extends JpaRepository<Produit, Integer> {


}
