package com.imt.framework.web.tuto.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.imt.framework.web.tuto.application.Client;


@Component
public interface ClientRepository extends JpaRepository<Client, Integer> {

}
