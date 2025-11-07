package com.tp.microservice.client.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.tp.microservice.client.application.Client;


@Component
public interface ClientRepository extends JpaRepository<Client, Integer> {

}
