package com.matheus.apiestacionamento.web.controller;

import com.matheus.apiestacionamento.Service.UsuarioService;
import com.matheus.apiestacionamento.entities.Usuario;
import com.matheus.apiestacionamento.web.dto.UsuarioCreateDto;
import com.matheus.apiestacionamento.web.dto.UsuarioResponseDto;
import com.matheus.apiestacionamento.web.dto.UsuarioSenhaDto;
import com.matheus.apiestacionamento.web.dto.mapper.UsuarioMapper;
import com.matheus.apiestacionamento.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Contem todas as operações CRUD de usuarios" )
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(
            summary = "Criar um novo usuario",
            description = "Recurso para criar novo usuario",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuario criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Usuario ja cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto obj) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(obj));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @Operation(
            summary = "Recuperar usuario por ID",
            description = "Recuperar usuario por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recusrso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }

    @Operation(
            summary = "Atualizar senha",
            description = "Atualizar senha",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Recusrso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Senha incorreta",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campo invalido ou mal formatado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDto obj) {
        Usuario user = usuarioService.editarSenha(id, obj.getSenhaAtual(), obj.getNovaSenha(), obj.getConfirmaSenha());
        return ResponseEntity.noContent().build(); // Retorna 204
    }

    @Operation(
            summary = "Listar todos os usuarios",
            description = "Listar todos os usuarios cadastrados",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Lista com todos os usuarios cadastrados",
                            content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDto.class)))),
            }
    )
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll() {
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }
}
