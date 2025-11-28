package com.matheus.apiestacionamento.repositories;

import com.matheus.apiestacionamento.entities.ClienteVaga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteVagaRepository extends JpaRepository<ClienteVaga, Long> {
}
