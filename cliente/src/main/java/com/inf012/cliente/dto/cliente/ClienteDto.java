package com.inf012.cliente.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteDto(
                @NotBlank(message = "Nome é obrigatório") @Size(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres") String nome,

                @NotBlank(message = "CPF é obrigatório") @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres") String cpf,

                @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido") String email,

                String telefone) {
}
