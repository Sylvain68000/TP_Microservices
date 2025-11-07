package com.imt.framework.web.tuto.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.imt.framework.web.tuto.application.Produit;


@Component
public interface ProduitRepository extends JpaRepository<Produit, Integer> {


}
