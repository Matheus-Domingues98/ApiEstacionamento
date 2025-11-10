package com.matheus.apiestacionamento.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class UsuarioCreateDto {

    @NotBlank(message = "Username é obrigatório")
    @Email(message = "Formato do e-mail invalido", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String username;

    @NotBlank(message = "Password é obrigatório")
    @Size(min = 6, max = 6, message = "Password deve ter exatamente 6 caracteres")
    private String password;
}
