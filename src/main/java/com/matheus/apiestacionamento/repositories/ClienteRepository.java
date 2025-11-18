package com.matheus.apiestacionamento.repositories;

import com.matheus.apiestacionamento.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
