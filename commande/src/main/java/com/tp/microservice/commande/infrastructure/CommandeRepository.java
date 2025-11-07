package com.tp.microservice.commande.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tp.microservice.commande.application.Commande;


public interface  CommandeRepository extends JpaRepository<Commande, Integer> {

}
