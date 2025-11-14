package com.matheus.apiestacionamento.repositories;

import com.matheus.apiestacionamento.entities.Usuario;
import com.matheus.apiestacionamento.entities.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

    @Query("select u.role from Usuario u where u.username like :username")
    Role findRoleByUsername(String username);
}
