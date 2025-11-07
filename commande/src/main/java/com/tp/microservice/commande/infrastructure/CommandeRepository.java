package com.imt.framework.web.tuto.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.imt.framework.web.tuto.application.Commande;

@Component
public interface  CommandeRepository extends JpaRepository<Commande, Integer> {

}
