package com.matheus.apiestacionamento.Service;

import com.matheus.apiestacionamento.entities.Usuario;
import com.matheus.apiestacionamento.entities.enums.Role;
import com.matheus.apiestacionamento.exception.EntityNotFoundException;
import com.matheus.apiestacionamento.exception.PasswordArgumentNotValidException;
import com.matheus.apiestacionamento.exception.UsernameUniqueViolationException;
import com.matheus.apiestacionamento.jwt.JwtUtils;
import com.matheus.apiestacionamento.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(Usuario obj) {
        try {
            obj.setPassword(passwordEncoder.encode(obj.getPassword()));
            return usuarioRepository.save(obj);
        } catch (DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(String.format("Username '%s' ja cadastrado", obj.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario id =%s nao encontrado", id))
        );
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)) {
            throw new PasswordArgumentNotValidException(String.format("Senha do id = %s não confere", id));
        }

        Usuario user = buscarPorId(id);

        if (!passwordEncoder.matches(senhaAtual, user.getPassword())) {
            throw new PasswordArgumentNotValidException(String.format("Senha atual do id = %s não confere", id));
        }
        user.setPassword(passwordEncoder.encode(novaSenha));
        return user;
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario com '%s nao encontrado", username))
        );
    }

    public Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }
}
