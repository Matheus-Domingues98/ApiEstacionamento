package com.matheus.apiestacionamento.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UsuarioSenhaDto {

    @NotBlank(message = "Senha atual é obrigatória")
    @Size(min = 6, max = 6, message = "Senha atual deve ter exatamente 6 caracteres")
    private String senhaAtual;

    @NotBlank(message = "Senha atual é obrigatória")
    @Size(min = 6, max = 6, message = "Nova senha deve ter exatamente 6 caracteres")
    private String novaSenha;

    @NotBlank(message = "Senha atual é obrigatória")
    @Size(min = 6, max = 6, message = "Senha de confirmação deve ter exatamente 6 caracteres")
    private String confirmaSenha;
}
