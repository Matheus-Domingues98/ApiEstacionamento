package com.matheus.apiestacionamento.repositories;

import com.matheus.apiestacionamento.entities.StatusVaga;
import com.matheus.apiestacionamento.entities.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VagasRepository extends JpaRepository<Vaga, Long> {
    Optional<Vaga> findByCodigo(String codigo);

    Optional<Vaga> findFirstByStatus(StatusVaga statusVaga);
}
