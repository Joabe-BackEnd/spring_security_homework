package com.homework.security.dto;


import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestDTO(

        @NotBlank(message = "As credenciais devem ser preenchidas corretamente.")
        String nome,
        @NotBlank(message = "As credenciais devem ser preenchidas corretamente.")
        String senha
) {
}
