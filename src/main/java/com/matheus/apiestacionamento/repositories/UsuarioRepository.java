package com.matheus.apiestacionamento.repositories;

import com.matheus.apiestacionamento.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
