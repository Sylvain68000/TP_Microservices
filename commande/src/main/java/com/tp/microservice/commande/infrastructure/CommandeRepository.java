package com.tp.microservice.commande.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tp.microservice.commande.application.Commande;


public interface  CommandeRepository extends JpaRepository<Commande, Integer> {
    List<Commande> findByIdIn(List<Integer> ids);
    List<Commande> findByClientId(Integer clientId);

}
