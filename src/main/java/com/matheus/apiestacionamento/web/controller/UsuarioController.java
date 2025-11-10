package com.matheus.apiestacionamento.web.controller;

import com.matheus.apiestacionamento.Service.UsuarioService;
import com.matheus.apiestacionamento.entities.Usuario;
import com.matheus.apiestacionamento.web.dto.UsuarioCreateDto;
import com.matheus.apiestacionamento.web.dto.UsuarioResponseDto;
import com.matheus.apiestacionamento.web.dto.UsuarioSenhaDto;
import com.matheus.apiestacionamento.web.dto.mapper.UsuarioMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto obj) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(obj));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDto obj) {
        Usuario user = usuarioService.editarSenha(id, obj.getSenhaAtual(), obj.getNovaSenha(), obj.getConfirmaSenha());
        return ResponseEntity.noContent().build(); // Retorna 204
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll() {
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }
}
