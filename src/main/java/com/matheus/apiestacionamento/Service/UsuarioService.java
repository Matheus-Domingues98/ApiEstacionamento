package com.matheus.apiestacionamento.Service;

import com.matheus.apiestacionamento.entities.Usuario;
import com.matheus.apiestacionamento.exception.EntityNotFoundException;
import com.matheus.apiestacionamento.exception.PasswordArgumentNotValidException;
import com.matheus.apiestacionamento.exption.UsernameUniqueViolationException;
import com.matheus.apiestacionamento.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario obj) {
        try {
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

        if (!user.getPassword().equals(senhaAtual)) {
            throw new PasswordArgumentNotValidException(String.format("Senha atual do id = %s não confere", id));
        }

        user.setPassword(novaSenha);
        return user;
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }
}
