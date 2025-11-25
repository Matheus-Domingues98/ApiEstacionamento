package com.matheus.apiestacionamento.web.controller;

import com.matheus.apiestacionamento.service.ClienteService;
import com.matheus.apiestacionamento.service.UsuarioService;
import com.matheus.apiestacionamento.entities.Cliente;
import com.matheus.apiestacionamento.jwt.JwtUserDetails;
import com.matheus.apiestacionamento.repositories.projection.ClienteProjection;
import com.matheus.apiestacionamento.web.dto.ClienteCreateDto;
import com.matheus.apiestacionamento.web.dto.ClienteResponseDto;
import com.matheus.apiestacionamento.web.dto.PageableDto;
import com.matheus.apiestacionamento.web.dto.mapper.ClienteMapper;
import com.matheus.apiestacionamento.web.dto.mapper.PageableMapper;
import com.matheus.apiestacionamento.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;


@Tag(name = "Clientes", description = "Contem todas as operações relativas ao recurso de um clientes")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    @Operation(
            summary = "Criar um novo cliente",
            description = "Recurso para criar novo cliente vinculado a um usuario cadastrado. " +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='CLIENTE' ",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Cliente CPF ja possui cadastro no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recusrso não permitido ao perfil de ADMIN",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> create(@RequestBody @Valid ClienteCreateDto dto,
                                                     @AuthenticationPrincipal JwtUserDetails userDetails) {
        Cliente cliente = ClienteMapper.toCliente(dto);
        cliente.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
        clienteService.salvar(cliente);

        return ResponseEntity.status(201).body(ClienteMapper.toDto(cliente));
    }

    @Operation(
            summary = "Recuperar cliente por ID",
            security = @SecurityRequirement(name = "security"),
            description = "Recurso para localizar um cliente por ID." +
                    "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso localizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil de CLIENTE",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClienteResponseDto> getById(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return  ResponseEntity.ok(ClienteMapper.toDto(cliente));
    }

    @Operation(
            summary = "Recuperar lista de clientes",
            security = @SecurityRequirement(name = "security"),
            description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN",
            parameters = {
                    @Parameter(in = QUERY, name = "page",
                        content = @Content(schema = @Schema(type = "integer", defaultValue = "20")),
                        description = "Representa a pagina retornada."
                    ),
                    @Parameter(in = QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "20")),
                            description = "Representa o total de elementos por pagina."
                    ),
                    @Parameter(in = QUERY, name = "sort", hidden = true,
                            array = @ArraySchema(schema = @Schema(type = "String", defaultValue = "id,asc")),
                            description = "Representa a ordenação dos resultados. Aceita multiplos criterios de ordenação."
                    ),
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json; charset=utf-8",
                                    schema = @Schema(implementation = ClienteResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil de 'CLIENTE'",
                            content = @Content(mediaType = "application/json; charset=utf-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAll(@Parameter(hidden = true) @PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
        Page<ClienteProjection> clientes = clienteService.buscarTodos(pageable);
        return  ResponseEntity.ok(PageableMapper.toDto(clientes));
    }

    @Operation(
            summary = "Recuperar dados do cliente autenticado",
            security = @SecurityRequirement(name = "security"),
            description ="Requisição exige uso de um bearer token. Acesso restrito a Role='CLIENTE'",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso localizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil de ADMIN",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/detalhes")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> getDetalhes(@AuthenticationPrincipal JwtUserDetails userDetails) {
        Cliente cliente = clienteService.buscarPorUsuarioId(userDetails.getId());
        return  ResponseEntity.ok(ClienteMapper.toDto(cliente));
    }
}
